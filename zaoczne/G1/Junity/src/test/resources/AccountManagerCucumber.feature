Feature: AccountManager class test

Scenario: Internal payment, all is ok
    Given SetUpTestEnv
    Given We have user "Tomasz" with id: 1
    Given "Tomasz" have account: 2 with: 1000 pln
    Given There is an account:3 with 100 pln
    Given Everything is authorised
    When "Tomasz" make transfer from acc: 2 to acc: 3 with ammount: 200
    Then account: 2 value: 800 pln
    Then account: 3 value: 300 pln
    #Then all goes well

Scenario Outline: Proper Internal money flows
    Given SetUpTestEnv
    Given We have user "<user>" with id: 1
    Given "<user>" have account: <srcAcc> with: <srcAmount> pln
    Given There is an account:<dstAcc> with <dstAmount> pln
    Given Everything is authorised
    When "Tomasz" make transfer from acc: 2 to acc: 3 with ammount: 200
    #When "<user>" make transfer from acc: <srcAcc> to acc: <dstAcc> with ammount: <amount>
    Then account: 2 value: 800 pln
    Then account: 3 value: 300 pln
#        Then Operation is successful
#        Then Expect updates only on proper accounts
#            |<srcAcc>|<dstAcc>|
#        Then Withdraw was logged properly
#        Then Payment was logged properly

        Examples:
            |user   |srcAcc |srcAmount  |dstAcc |dstAmount  |amount |expSrc |expDst |
            |Tomasz |1      |200        |2      |100        |158.2  |41.8   |258.2  |
            |Tomasz |1      |200        |3      |100        |200    |0      |300    |
            |Alicja |3      |1000       |1      |0          |100    |900    |100    |


