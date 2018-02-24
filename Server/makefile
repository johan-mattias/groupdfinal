USER = root
IP = 188.166.170.111

# PATH IS ONLY FOR EDVINS LAPTOP
startSSHAgent:
	@eval $(ssh-agent)
	@ssh-add ~/.ssh/github

ssh: 
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

localResetTables:
	@node sql/dropTables.js
	@node sql/createTables.js
	@node sql/fillDummy.js

dropTables:
	@ssh -C $(USER)@$(IP) "node ~/Platform-spanning/Server/sql/dropTables.js"

sendTestImage:
	@curl \
  		-F "user=1" \
  		-F "beer=1" \
  		-F "image=@/mnt/d/Downloads/507985.jpg" \
  		localhost:8080/image
