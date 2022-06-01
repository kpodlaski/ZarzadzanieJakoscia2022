package biz;

import db.dao.DAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Account;
import model.User;
import model.exceptions.OperationIsNotAllowedException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoneyTransferTests {

    @Mock
    DAO daoMock;
    @Mock
    AuthenticationManager authMock;
    @Mock
    BankHistory histMock;
    @Mock
    InterestOperator intrestMock;

    AccountManager aM;
    List<User> users = new ArrayList<User>();

    @Given("SetUpTestEnv")
    public void setUpTestEnv() throws NoSuchFieldException, IllegalAccessException {
        //... set up
        daoMock = mock(DAO.class);
        aM = new AccountManager();
        Field daoField = aM.getClass().getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(aM,daoMock);
        authMock = mock(AuthenticationManager.class);
        aM.auth = authMock;
        intrestMock = mock(InterestOperator.class);
        aM.interestOperator = intrestMock;
        histMock = mock(BankHistory.class);
        aM.history=histMock;
    }

    @Given("We have user {string} with id: {int}")
    public void setUpUserWithNameAndId(String name, int id) throws SQLException {
        User user = new User();
        user.setName(name);
        user.setId(id);
        users.add(user);
        when(daoMock.findUserByName(name)).thenReturn(user);
    }

    @Given("{string} have account: {int} with: {double} pln")
    public void setUpAccountWithNameIdandAmount(String name, int accId, double amount ) throws SQLException {
        User u = null;
        for (User _u: users ) {
            if (_u.getName().equals(name)){
                u = _u;
            }
        }
        if (u == null) throw new NullPointerException();
        Account acc = setupAccountWithIdandAmount(accId,amount);
        acc.setOwner(u);
    }

    @Given("There is an account:{int} with {double} pln")
    public Account setupAccountWithIdandAmount(int accId, double amount) throws SQLException {
        Account acc = new Account();
        acc.setId(accId);
        acc.setAmmount(amount);
        when(daoMock.findAccountById(accId)).thenReturn(acc);
        return acc;
    }

    @Given("Everything is authorised")
    public void authorizeEverything(){
        when(authMock.canInvokeOperation(any(), any())).thenReturn(true);
    }

    @When("{string} make transfer from acc: {int} to acc: {int} with ammount: {double}")
    public void makeTransfer(String name, int srcId, int dstId, double amount) throws OperationIsNotAllowedException, SQLException {
        User u = null;
        for (User _u: users ) {
            if (_u.getName().equals(name)){
                u = _u;
            }
        }
        if (u == null) throw new NullPointerException();
        aM.internalPayment(u, amount, "Opis", srcId, dstId);
    }

    @Then("account:{int} value:{double} pln")
    public void checkAccountAmount(int accId, double value) throws SQLException {
        Account acc = daoMock.findAccountById(accId);
        System.out.println(accId);
        assertEquals(acc.getAmmount(),value,0.001);
    }
}
