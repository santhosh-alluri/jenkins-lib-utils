/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

void call(String workspace, String product, String cluster, List svcs, String awsCredentialsId, String awsRegion) {
  withAWS(credentials: awsCredentialsId, region: awsRegion) {
    for (String service in services.join(' ')) {
      sh script: "aws ecs update-service --cluster ${cluster} --service ${service} --force-new-deployment"
    }
  }
}
