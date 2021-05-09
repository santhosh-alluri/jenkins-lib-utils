/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

void call(String repo, String tag, Map execCfg) {
  withAWS(credentials: execCfg.awsCredentialsId, region: execCfg.awsRegion) {
    sh label: 'login to ECR', script: """
      aws ecr get-login-password | docker login --username AWS \
        --password-stdin ${execCfg.awsAccountId}.dkr.ecr.${execCfg.awsRegion}.amazonaws.com
    """
    sh "docker push ${repo}:${tag}"
    sh 'docker logout'
  }
}
