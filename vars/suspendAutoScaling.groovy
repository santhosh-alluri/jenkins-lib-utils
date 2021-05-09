/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

/* groovylint-disable LineLength */
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
