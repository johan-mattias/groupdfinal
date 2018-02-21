USER = root
IP = 188.166.170.111

# PATH IS ONLY FOR EDVINS LAPTOP
startSSHAgent:
	@eval $(ssh-agent)
	@ssh-add ~/.ssh/github

ssh: 
	@ssh $(USER)@$(IP)

runServer: 
	@ssh -C $(USER)@$(IP) "pm2 start ~/Platform-spanning/Server/app.js"

restartSever:
	@ssh -C $(USER)@$(IP) "pm2 restart app"

startMySQL: 
	@ssh -C $(USER)@$(IP) "sudo service mysql start"

