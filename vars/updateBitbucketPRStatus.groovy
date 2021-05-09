/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

import java.text.Normalizer
import java.text.Normalizer.Form
import java.util.regex.Pattern

void call(Map bitbucketCfg, String status) {
  projectKey         = bitbucketCfg.projectKey
  pullRequestId      = env.CHANGE_ID
  repoName           = determineRepoName()
  repositorySlug     = toSlug(repoName)
  encodedTokenString = getBasicAuthCredentials(bitbucketCfg.httpsCredentialsId)
  userSlug           = 'jenkins'

  // Docs: https://docs.atlassian.com/bitbucket-server/rest/6.7.1/bitbucket-rest.html#idp319
  endpoint = "${bitbucketCfg.apiEndpoint}/rest/api/1.0/projects/" +
    "${projectKey}/repos/${repositorySlug}/pull-requests/${pullRequestId}/participants/${userSlug}"

  // create payload
  body = """
      {"status": "${status}"}
  """

  requestType = 'APPLICATION_JSON'
  response = httpRequest consoleLogResponseBody: true,
    acceptType: requestType,
    contentType: requestType,
    httpMode: 'PUT',
    requestBody: body,
    url: "${endpoint}",
    customHeaders: [[name: 'Authorization', value: "Basic ${encodedTokenString}", maskValue: true]]
}

String toSlug(String input) {
  Pattern nonLatin = Pattern.compile('[^\\w-]')
  Pattern whitespace = Pattern.compile('[\\s]')

  String nowhitespace = whitespace.matcher(input).replaceAll('-')
  String normalized = Normalizer.normalize(nowhitespace, Form.NFD)
  String slug = nonLatin.matcher(normalized).replaceAll('')
  return slug.toLowerCase(Locale.ENGLISH)
}

String determineRepoName() {
  /* groovylint-disable-next-line DuplicateNumberLiteral, UnnecessaryGString, UnnecessaryGetter */
  return scm.getUserRemoteConfigs()[0].getUrl().tokenize('/').last().split("\\.")[0]
}

/* groovylint-disable-next-line ImplicitReturnStatement */
String getBasicAuthCredentials(String httpsCredentialsId) {
  withCredentials([
    usernamePassword(
      credentialsId: httpsCredentialsId, passwordVariable: 'BB_PASS', usernameVariable: 'BB_USR'
    )
  ]) {
    return "$BB_USR:$BB_PASS".bytes.encodeBase64().toString()
  }
}
