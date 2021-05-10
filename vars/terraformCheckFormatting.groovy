/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

void call() {
  sh label: 'terraform fmt', script: 'terraform fmt -recursive -check -diff'
}
