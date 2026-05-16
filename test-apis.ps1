# 测试所有后端接口
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Ripple 后端接口全面测试" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$BaseUrl = "http://localhost:8883/ripple"
$Token = $null

# 测试 1: 登录接口
Write-Host "=== 测试 1: 登录接口 (POST /login/check) ===" -ForegroundColor Yellow
$Body = @{account="admin"; password="123"}
try {
    $LoginRes = Invoke-RestMethod -Uri "$BaseUrl/login/check" -Method Post -Body $Body -ContentType "application/x-www-form-urlencoded"
    Write-Host "✅ 登录成功!" -ForegroundColor Green
    $Token = $LoginRes.data.token
    Write-Host "Token: $($Token.Substring(0, 50))..." -ForegroundColor Gray
    Write-Host ""
} catch {
    Write-Host "❌ 登录失败: $_" -ForegroundColor Red
    Write-Host ""
}

# 测试 2: 未登录访问受保护接口
Write-Host "=== 测试 2: 未登录访问受保护接口 ===" -ForegroundColor Yellow
try {
    $Res = Invoke-RestMethod -Uri "$BaseUrl/user/findAllXml" -Method Post -ErrorAction SilentlyContinue
    Write-Host "意外: 应该被拦截!" -ForegroundColor Red
} catch {
    Write-Host "✅ 未登录被正确拦截!" -ForegroundColor Green
}
Write-Host ""

# 测试 3: 查询所有用户(XML) - 带token
Write-Host "=== 测试 3: 查询所有用户(XML) (POST /user/findAllXml) ===" -ForegroundColor Yellow
try {
    $Headers = @{"Authorization"="Bearer $Token"}
    $Res = Invoke-RestMethod -Uri "$BaseUrl/user/findAllXml" -Method Post -Headers $Headers
    Write-Host "✅ 成功查询用户!" -ForegroundColor Green
    Write-Host "用户数: $($Res.result.Count)" -ForegroundColor Gray
} catch {
    Write-Host "❌ 失败: $_" -ForegroundColor Red
}
Write-Host ""

# 测试 4: 查询所有用户(注解)
Write-Host "=== 测试 4: 查询所有用户(注解) (POST /user/findAllAnnotation) ===" -ForegroundColor Yellow
try {
    $Headers = @{"Authorization"="Bearer $Token"}
    $Res = Invoke-RestMethod -Uri "$BaseUrl/user/findAllAnnotation" -Method Post -Headers $Headers
    Write-Host "✅ 成功查询!" -ForegroundColor Green
    Write-Host "用户数: $($Res.result.Count)" -ForegroundColor Gray
} catch {
    Write-Host "❌ 失败: $_" -ForegroundColor Red
}
Write-Host ""

# 测试 5: 按名称查询用户
Write-Host "=== 测试 5: 按名称查询用户 (POST /user/findUserByName) ===" -ForegroundColor Yellow
try {
    $Headers = @{"Authorization"="Bearer $Token"}
    $Res = Invoke-RestMethod -Uri "$BaseUrl/user/findUserByName?userName=admin" -Method Post -Headers $Headers
    Write-Host "✅ 成功查询!" -ForegroundColor Green
    Write-Host "用户: $($Res.name)" -ForegroundColor Gray
} catch {
    Write-Host "❌ 失败: $_" -ForegroundColor Red
}
Write-Host ""

# 测试 6: MyBatis-Plus查询
Write-Host "=== 测试 6: MyBatis-Plus查询 (POST /user/findAllByMyPlus) ===" -ForegroundColor Yellow
try {
    $Headers = @{"Authorization"="Bearer $Token"}
    $Body2 = @{name=""}
    $Res = Invoke-RestMethod -Uri "$BaseUrl/user/findAllByMyPlus" -Method Post -Headers $Headers -Body $Body2 -ContentType "application/x-www-form-urlencoded"
    Write-Host "✅ 成功查询!" -ForegroundColor Green
} catch {
    Write-Host "❌ 失败: $_" -ForegroundColor Red
}
Write-Host ""

# 测试 7: 序列接口
Write-Host "=== 测试 7: 序列接口 (GET /sequence/get) ===" -ForegroundColor Yellow
try {
    $Headers = @{"Authorization"="Bearer $Token"}
    $Res = Invoke-RestMethod -Uri "$BaseUrl/sequence/get?type=USER" -Method Get -Headers $Headers
    Write-Host "✅ 成功获取序列!" -ForegroundColor Green
    Write-Host "序列值: $($Res.result)" -ForegroundColor Gray
} catch {
    Write-Host "❌ 失败: $_" -ForegroundColor Red
}
Write-Host ""

# 测试 8: 框架接口
Write-Host "=== 测试 8: 框架接口 (GET /framework/getSysProperty) ===" -ForegroundColor Yellow
try {
    $Headers = @{"Authorization"="Bearer $Token"}
    $Res = Invoke-RestMethod -Uri "$BaseUrl/framework/getSysProperty" -Method Get -Headers $Headers
    Write-Host "✅ 成功获取系统信息!" -ForegroundColor Green
} catch {
    Write-Host "❌ 失败: $_" -ForegroundColor Red
}
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "测试完成!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
