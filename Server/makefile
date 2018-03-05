USER = root
IP = 188.166.170.111


SHELL := /bin/bash
# PATH IS ONLY FOR EDVINS LAPTOP
startSSHAgent:
	@eval $(ssh-agent)
	@ssh-add ~/.ssh/github

ssh: startSSHAgent
	@ssh -t $(USER)@$(IP) "cd ~/Platform-spanning/Server; bash"

runServer: 
	@ssh -C $(USER)@$(IP) "pm2 start ~/Platform-spanning/Server/app.js"

restartServer:
	@ssh -C $(USER)@$(IP) "pm2 restart app"

stopServer:
	@ssh -C $(USER)@$(IP) "pm2 stop app"

startMySQL: 
	@ssh -C $(USER)@$(IP) "sudo service mysql start"

updateServer:
	@ssh -C $(USER)@$(IP) "git -C ~/Platform-spanning/Server pull"

createNewTables:
	@ssh -C $(USER)@$(IP) "node ~/Platform-spanning/Server/sql/createTables.js"

localDropTables:
	@node sql/dropTables.js
	@node sql/dropViews.js

localCreateTables:
	@node sql/createTables.js
	@node sql/createViews.js
	@node sql/fillDummy.js

dropTables:
	@ssh -C $(USER)@$(IP) "node ~/Platform-spanning/Server/sql/dropTables.js"




sendTestImage:
	@curl \
  		-F "userID=1" \
  		-F "beerID=1" \
  		-F "image=@/mnt/d/Downloads/507985.jpg" \
		-F "description=hejsan!!!!" \
  		188.166.170.111:8080/image/upload

sendLocalTestImage:
	@curl \
		-F "userID=1" \
  		-F "beerID=1" \
  		-F "image=@/mnt/d/Downloads/507985.jpg" \
		-F "description=hejsan!!!!" \
  		localhost:8080/image/upload

fillLocalComments:
	@echo hej! \
	    image=1 ; while [[ $$image -le 2 ]] ; do \
        comment=1 ; while [[ $$comment -le 30 ]] ; do \
		curl \
			-F "user=1" \
			-F "image=$$image" \
			-F "comment=dummy $$comment" \
			localhost:8080/comment/upload ;\
            ((comment = comment + 1)) ; \
			echo HEJ!! ;\
        done ; \
        ((image = image + 1)) ; \
    done ;

fillComments:
		image=1 ; while [[ $$image -le 2 ]] ; do \
			commen=1 ; while [[ $$comment -le 30 ]] ; do \
				curl -F "user=1" -F "image=$$image" -F "comment=dummy $$comment" 188.166.170.111:8080/comment/upload ;\
				((comment = comment + 1)) ; \
			done ; \
        ((image = image + 1)) ; \
    done ;

target:
	@echo hej! \
    num1=1 ; while [[ $$num1 -le 4 ]] ; do \
        num2=1 ; while [[ $$num2 -le 3 ]] ; do \
            echo $$num1 $$num2 ; \
            ((num2 = num2 + 1)) ; \
        done ; \
        ((num1 = num1 + 1)) ; \
    done