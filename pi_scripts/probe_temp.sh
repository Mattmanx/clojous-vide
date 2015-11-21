#!/bin/bash  
filename="/sys/bus/w1/devices/28*/w1_slave"
eval "sed '2q;d' $filename | sed -r 's/.*(t=)([0-9]{2})([0-9]{3})/\2.\3/'" 
