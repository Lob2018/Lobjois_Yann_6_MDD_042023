@echo off
	REG delete "HKCU\Environment" /F /V "MDD_YL_API_MYSQL_USERNAME"
	REG delete "HKCU\Environment" /F /V "MDD_YL_API_MYSQL_PASSWORD"
	REG delete "HKCU\Environment" /F /V "MDD_YL_API_JWTSECRET"
	REG delete "HKCU\Environment" /F /V "MDD_YL_API_JWTISSUER"	
exit