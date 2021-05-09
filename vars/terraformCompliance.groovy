/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

/* groovylint-disable-next-line LineLength */
void call(String pathToPlanFile, String pathToFeatures, String pathToTerraform, String sshFileCredentialsId, Map execCfg, String complianceDockerImage='tonyvankenobi/terraform-compliance:test') {

  withAWS(credentials: execCfg.awsCredentialsId, region: execCfg.awsRegion) {
    sh "terraform show -json ${pathToPlanFile} > plan.json"
    withCredentials([file(credentialsId: sshFileCredentialsId, variable: 'ID_RSA')]) {
      sh """mv ${ID_RSA} id_rsa
      docker run --pull=always --rm -v \$(pwd):/target -i ${complianceDockerImage} \
            -S -f ${pathToFeatures} -p plan.json -t ${pathToTerraform} -i id_rsa"""
    }
  }

}
