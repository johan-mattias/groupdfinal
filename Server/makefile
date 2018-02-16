USER = root
IP = 188.166.170.111

startSSHAgent:
	@eval `ssh-agent -s`
	@ssh-add ~/.ssh/id_rsa

ssh: startSSHAgent
	@ssh $(USER)@$(IP)

runServer:
	@ssh -C $(USER)@$(IP) "pm2 start ~/Platform-spanning/Server/app.js"

restartSever:
	@ssh -C $(USER)@$(IP) "pm2 restart app"

