package biz;

import db.dao.DAO;
import io.cucumber.java.en.Given;
import model.Account;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        Account acc = new Account();
        acc.setId(accId);
        acc.setAmmount(amount);
        User u = null;
        for (User _u: users ) {
            if (_u.getName().equals(name)){
                u = _u;
            }
        }
        if (u == null) throw new NullPointerException();
        acc.setOwner(u);
        when(daoMock.findAccountById(accId)).thenReturn(acc);
    }
}
