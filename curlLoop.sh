#!/bin/bash - 
#===============================================================================
#
#          FILE: curlLoop.sh
# 
#         USAGE: ./curlLoop.sh 100000
# 
#   DESCRIPTION: 
# 
#       OPTIONS: ---
#  REQUIREMENTS: ---
#          BUGS: ---
#         NOTES: ---
#       CREATED: 01/16/2020 14:45
#      REVISION:  ---
#===============================================================================

set -o nounset                              # Treat unset variables as an error

for i in $(seq $1); do
  curl http://0.0.0.0:1337/$i
done
