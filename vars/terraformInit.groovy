/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

void call(String awsCredentialsId, String awsRegion, String sshCredentialsId='') {
  withAWS(credentials: awsCredentialsId, region: awsRegion) {
    // Configuration might not have any external private modules, so we handle that
    if (sshCredentialsId == '') {
      tfInit()
    } else {
      sshagent([sshCredentialsId]) {
        tfInit()
      }
    }
  }
}

void tfInit() {
  sh 'terraform init'
}
