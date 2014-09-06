# deploy host files ... 
# reads host config file from 


HOST=photos.calldei.com
rsync  --delete -r --rsh "ssh -i /cygdrive/c/odd_bin/Nexstra.pem" --chmod=ugo=rx -v \
  --exclude=*.symbolMap --delete-excluded war/* xmlsh@$HOST:/home/xmlsh/speedtest/

echo Done
