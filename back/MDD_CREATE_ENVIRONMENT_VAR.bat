@echo off
	chcp 65001	
	set /p "mysqlUser=Enter MDD MySQL username : "
	setx MDD_YL_API_MYSQL_USERNAME %mysqlUser%
	set /p "mysqlPassword=Enter MDD MySQL password : "
	setx MDD_YL_API_MYSQL_PASSWORD %mysqlPassword%
	set /p "jwtSecret=Enter MDD JWT secret : "
	setx MDD_YL_API_JWTSECRET %jwtSecret%
	set /p "jwtIssuer=Enter MDD JWT issuer : "
	setx MDD_YL_API_JWTISSUER %jwtIssuer%	
exit