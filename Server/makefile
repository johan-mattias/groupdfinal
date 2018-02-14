USER = root
IP = 188.166.170.111

startSSHAgent:
	@eval `ssh-agent -s`
	@ssh-add ~/.ssh/id_rsa

ssh: startSSHAgent
	@ssh $(USER)@$(IP)


