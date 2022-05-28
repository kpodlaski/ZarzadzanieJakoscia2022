package biz;

import db.dao.DAO;
import db.dao.NoSuchAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Account;
import model.User;
import model.exceptions.OperationIsNotAllowedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class AccountManagerCucumber {
    AccountManager aM = new AccountManager();
    @Mock
    DAO mockDao;
    @Mock
    BankHistory mockHistory;


    @BeforeEach
    void setUp() {
        mockDao = mock(DAO.class);
        mockHistory = mock(BankHistory.class);
        Field daoField;
        try {
            daoField = AccountManager.class.getDeclaredField("dao");
            daoField.setAccessible(true);
            daoField.set(aM,mockDao);
            daoField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //aM.dao=mockDao;
        aM.history=mockHistory;
    }

    @AfterEach
    void tearDown() {
    }


    @Given("We have user {string} with id: {int}")
    void givenWeHaveUser(String userName, int userId) throws SQLException {
        User testUser = new User();
        testUser.setName(userName);
        testUser.setId(userId);
        when(mockDao.findUserByName(userName)).thenReturn(testUser);
    }

    @Given("{string} have account: {int} with: {double} pln")
    void setUpAccount(String userName, int accountId, double value) throws SQLException {
        //Wyszukać usera o nazwie userName używając dao
        //Robimy troszę na sztywno, jest to złe podejście
        User user = mockDao.findUserByName(userName);
        Account account = setUpAccounNoUserSet(accountId, value);
        account.setOwner(user);
    }

    @Given( "There is an account: {int}  with {double} pln")
    Account setUpAccounNoUserSet(int accountId, double value) throws SQLException {
        Account account = new Account();
        account.setId(accountId);
        account.setAmmount(value);
        when(mockDao.findAccountById(accountId)).thenReturn(account);
        return account;
    }

    @When("{string} make transfer from acc: {int} to acc: {int} with ammount: {double}")
    void makeTransfer(String userName, int srcAccId, int destAccId, double value) throws SQLException, OperationIsNotAllowedException {
        User user = mockDao.findUserByName(userName);
        aM.internalPayment(user,value,"Some desription",srcAccId,destAccId);
    }

    @Then("account: {int} value: {double} pln")
    void checkStateOfAccount(int accountId, double value) throws SQLException {
        Account acc = mockDao.findAccountById(accountId);
        assertEquals(value, acc.getAmmount());
    }
}
