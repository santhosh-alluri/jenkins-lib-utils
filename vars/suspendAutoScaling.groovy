void call(String suspend, String serviceNamespace, String scalableDimension, String resourceId, String awsCredentialsId, String awsRegion) {
  withAWS(credentials: awsCredentialsId, region: awsRegion) {
    sh(script: """
      aws application-autoscaling register-scalable-target --service-namespace ${serviceNamespace} \
        --scalable-dimension ${scalableDimension} --resource-id ${resourceId} \
        --suspended-state '{"DynamicScalingInSuspended":${suspend},\"DynamicScalingOutSuspended":${suspend}, "ScheduledScalingSuspended":${suspend}}'
      """
    )
  }
}
