/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

// Returns true if commit message contains [ci_skip] or [skip_ci]
boolean call() {
  // https://support.cloudbees.com/hc/en-us/articles/227266408-Current-Git-branch-is-HEAD-detached-at
  return sh(script: "git log -1 --pretty=%B | fgrep -ie '[migration]' -e '[migrate]'", returnStatus: true) == 0
}
