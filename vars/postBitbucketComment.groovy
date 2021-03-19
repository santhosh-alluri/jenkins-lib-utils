import java.text.Normalizer
import java.text.Normalizer.Form
import java.util.regex.Pattern

void call(bitbucketCfg, String comment) {
  projectKey         = bitbucketCfg.projectKey
  pullRequestId      = env.CHANGE_ID
  repoName           = determineRepoName()
  repositorySlug     = toSlug(repoName)
  encodedTokenString = getBasicAuthCredentials(bitbucketCfg.httpsCredentialsId)

  // Docs: https://docs.atlassian.com/bitbucket-server/rest/6.7.1/bitbucket-rest.html#idp214
  endpoint = "${bitbucketCfg.apiEndpoint}/rest/api/1.0/projects/" +
    "${projectKey}/repos/${repositorySlug}/pull-requests/${pullRequestId}/comments"

  // create payload
  body = """
      {"text": "${comment}"}
  """
  requestType = 'APPLICATION_JSON'
  response = httpRequest consoleLogResponseBody: true,
    acceptType: requestType,
    contentType: requestType,
    httpMode: 'POST',
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

String getBasicAuthCredentials(String httpsCredentialsId) {
  withCredentials([
    usernamePassword(
      credentialsId: httpsCredentialsId, passwordVariable: 'BB_PASS', usernameVariable: 'BB_USR'
    )
  ]) {
    return "$BB_USR:$BB_PASS".bytes.encodeBase64().toString()
  }
}
