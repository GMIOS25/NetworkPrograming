# Test Runner for Java Network Programming Examples
$ErrorActionPreference = "Continue"

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host " 1. KIEM TRA CAC VÍ DU CO BAN (GP CODER 3664)" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

Write-Host "`n--> [1.1] Chay UrlExample:" -ForegroundColor Yellow
java -cp bin com.gpcoder.net.UrlExample

Write-Host "`n--> [1.2] Chay URLConnectionExample:" -ForegroundColor Yellow
java -cp bin com.gpcoder.net.URLConnectionExample

Write-Host "`n--> [1.3] Chay InetAddressExample:" -ForegroundColor Yellow
java -cp bin com.gpcoder.net.InetAddressExample

Write-Host "`n==========================================" -ForegroundColor Cyan
Write-Host " 2. KIEM TRA VIETTUTS TCP CLIENT - SERVER" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

$vtPort = 6060
$vtServerJob = Start-Job -ScriptBlock {
    param($cwd, $port)
    Set-Location $cwd
    java -cp bin vn.viettuts.server.ServerExample $port
} -ArgumentList (Get-Location).Path, $vtPort

Start-Sleep -Milliseconds 1200

Write-Host "--> Khoi dong Client ket noi toi Server VietTuts:" -ForegroundColor Yellow
java -cp bin vn.viettuts.client.ClientExample localhost $vtPort

$vtServerLog = Receive-Job $vtServerJob -Wait -AutoRemoveJob
Write-Host "`n[Nhat ky Server VietTuts]:" -ForegroundColor Gray
$vtServerLog | ForEach-Object { Write-Host "   $_" }

Write-Host "`n==========================================" -ForegroundColor Cyan
Write-Host " 3. KIEM TRA GP CODER TCP MULTI-SERVER & CLIENT" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

$tcpPort = 5000
$tcpServerJob = Start-Job -ScriptBlock {
    param($cwd, $port)
    Set-Location $cwd
    java -cp bin com.gpcoder.tcp.EchoChatMultiServer $port
} -ArgumentList (Get-Location).Path, $tcpPort

Start-Sleep -Milliseconds 1200

Write-Host "--> Khoi dong EchoChatClient ket noi toi MultiServer:" -ForegroundColor Yellow
java -cp bin com.gpcoder.tcp.EchoChatClient 127.0.0.1 $tcpPort

# Dung server job
Stop-Job $tcpServerJob
$tcpServerLog = Receive-Job $tcpServerJob
Remove-Job $tcpServerJob
Write-Host "`n[Nhat ky MultiServer TCP]:" -ForegroundColor Gray
$tcpServerLog | ForEach-Object { Write-Host "   $_" }

Write-Host "`n==========================================" -ForegroundColor Cyan
Write-Host " 4. KIEM TRA GP CODER UDP ECHO SERVER & CLIENT" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

$udpPort = 5001
$udpServerJob = Start-Job -ScriptBlock {
    param($cwd, $port)
    Set-Location $cwd
    java -cp bin com.gpcoder.udp.EchoServer $port
} -ArgumentList (Get-Location).Path, $udpPort

Start-Sleep -Milliseconds 1000

Write-Host "--> Gui ban tin UDP 1:" -ForegroundColor Yellow
java -cp bin com.gpcoder.udp.EchoClient 127.0.0.1 $udpPort "Chao UDP Server tu Antigravity!"

Write-Host "`n--> Gui ban tin UDP 2 (QUIT de dung server):" -ForegroundColor Yellow
java -cp bin com.gpcoder.udp.EchoClient 127.0.0.1 $udpPort "QUIT"

$udpServerLog = Receive-Job $udpServerJob -Wait -AutoRemoveJob
Write-Host "`n[Nhat ky Server UDP]:" -ForegroundColor Gray
$udpServerLog | ForEach-Object { Write-Host "   $_" }

Write-Host "`n==========================================" -ForegroundColor Cyan
Write-Host " 5. KIEM TRA GP CODER MULTICAST SENDER & RECEIVER" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

$receiverJob = Start-Job -ScriptBlock {
    param($cwd)
    Set-Location $cwd
    java -cp bin com.gpcoder.multicast.MulticastReceiver 3
} -ArgumentList (Get-Location).Path

Start-Sleep -Milliseconds 1500

Write-Host "--> Khoi dong MulticastSender (gui 3 ban tin toi 224.0.0.1):" -ForegroundColor Yellow
java -cp bin com.gpcoder.multicast.MulticastSender 3

$recvLog = Receive-Job $receiverJob -Wait -AutoRemoveJob
Write-Host "`n[Nhat ky MulticastReceiver]:" -ForegroundColor Gray
$recvLog | ForEach-Object { Write-Host "   $_" }

Write-Host "`n==========================================" -ForegroundColor Green
Write-Host " HOAN TAT TAT CA CAC KIEM TRA!" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
