MySQLトランザクションテスト(TranTest)
=====================================
MySQLにおいてのトランザクションの動きをテストします。  
トランザクションの有無、トランザクション分離レベル、FOR UPDATEの使用などなど。  
また、同時実行のテストのためにトランザクションを実行するスレッド数も指定可能です。  
随時テストロジックは追加していきます。  

開発環境
--------
Mac OS X 10.7.4(LION)  
JDK 1.7.0_05(Oracle)  
Scala 2.9.2(MacPorts)  
MySQL 5.1.63  
Eclipse Indigo(3.7 SR2) - Eclipse IDE for Java EE Developers  
SWT 3.7.2 + WindowBuilder 1.3.0  
Scala IDE for Eclipse 2.0.2  
EGit 2.0.0  

実行出来るテスト
----------------
### テスト1 ###
以下のテーブルの口座1から金額を減らし、口座2の金額を増やします。テストの都合上、UPDATE文一発ではなくSELECTで現在の金額を読んでからUPDATEで更新しています。  
| id | amount   |  
-----------------  
|  1 | 10000.00 |  
|  2 |     0.00 |  

当然、トランザクションを掛けないと結果は正しくなりません。  

画面イメージ
------------
![画面イメージ](https://github.com/marony/TranTest/blob/master/Screenshot1.png)
