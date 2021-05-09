/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

void call(String sourceImage, String targetImage) {
  sh "docker tag ${sourceImage} ${targetImage}"
}
