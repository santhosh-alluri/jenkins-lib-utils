void call(String repo, String tag, Map execCfg) {
  withAWS(credentials: execCfg.awsCredentialsId, region: execCfg.awsRegion) {
    sh label: 'login to ECR', script: """
      aws ecr get-login-password | docker login --username AWS \
        --password-stdin ${execCfg.awsAccountId}.dkr.ecr.${execCfg.awsRegion}.amazonaws.com
    """
    sh "docker pull ${repo}:${tag}"
    sh 'docker logout'
  }
}
