/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

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
