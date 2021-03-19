void call(String cluster, List services, String awsCredentialsId, String awsRegion) {
  servicesList = services.join(' ')
  withAWS(credentials: awsCredentialsId, region: awsRegion) {
    returnCode = sh(
      script: "aws ecs wait services-stable --cluster ${cluster} --services ${servicesList}", returnStatus: true
    )
    if (returnCode == 255) {
      /* groovylint-disable-next-line ThrowException */
      throw new Exception('Cluster is not healthy!')
    }
  }
}
