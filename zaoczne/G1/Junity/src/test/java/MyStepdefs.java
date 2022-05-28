import biz.AccountManager;
import biz.AuthenticationManager;
import biz.BankHistory;
import db.dao.DAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Account;
import model.Operation;
import model.User;
import model.exceptions.OperationIsNotAllowedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyStepdefs {
    AccountManager aM = new AccountManager();
    @Mock
    DAO mockDao;
    @Mock
    BankHistory mockHistory;
    @Mock
    AuthenticationManager mockAuth;


    @BeforeEach
    void setUp() {
        mockDao = mock(DAO.class);
        mockHistory = mock(BankHistory.class);
        mockAuth = mock(AuthenticationManager.class);
        Field daoField;
        Field histField;
        Field authField;
        try {
            daoField = AccountManager.class.getDeclaredField("dao");
            daoField.setAccessible(true);
            daoField.set(aM,mockDao);
            daoField.setAccessible(false);
            histField = AccountManager.class.getDeclaredField("history");
            histField.setAccessible(true);
            histField.set(aM,mockHistory);
            histField.setAccessible(false);
            authField = AccountManager.class.getDeclaredField("auth");
            authField.setAccessible(true);
            authField.set(aM,mockAuth);
            authField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //aM.dao=mockDao;

    }

    @AfterEach
    void tearDown() {
    }

    @Given("SetUpTestEnv")
    public void setuptestenv() {
        setUp();
    }

    @Given("We have user {string} with id: {int}")
    public void weHaveUserWithId(String userName, int userId) throws SQLException {
        User testUser = new User();
        testUser.setName(userName);
        testUser.setId(userId);
        when(mockDao.findUserByName(userName)).thenReturn(testUser);
    }

    @Given("{string} have account: {int} with: {int} pln")
    public void haveAccountWithPln(String userName, int accountId, int value) throws SQLException {
        User user = mockDao.findUserByName(userName);
        Account account = thereIsAnAccountWithPln(accountId, value);
        account.setOwner(user);
    }

    @Given("There is an account:{int} with {int} pln")
    public Account thereIsAnAccountWithPln(int accountId, int value) throws SQLException {
        Account account = new Account();
        account.setId(accountId);
        account.setAmmount(value);
        when(mockDao.findAccountById(accountId)).thenReturn(account);
        return account;
    }

    @Given("Everything is authorised")
    public void EverythingIsAuthorized() throws SQLException {
        when(mockAuth.canInvokeOperation(any(Operation.class),any(User.class))).thenReturn(true);
    }

    @When("{string} make transfer from acc: {int} to acc: {int} with ammount: {int}")
    public void makeTransferFromAccToAccWithAmmount(String userName, int srcAccId, int destAccId, int value) throws SQLException, OperationIsNotAllowedException {
        User user = mockDao.findUserByName(userName);
        aM.internalPayment(user,value,"Some desription",srcAccId,destAccId);
    }

    @Then("account: {int} value: {int} pln")
    public void accountValuePln(int accountId, int value) throws SQLException {
        Account acc = mockDao.findAccountById(accountId);
        assertEquals(value, acc.getAmmount());
    }
}
