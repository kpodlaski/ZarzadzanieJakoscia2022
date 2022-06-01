package biz;

import db.dao.DAO;
import model.Account;
import model.Operation;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountManagerTest {
    @Mock
    DAO daoMock;
    @Mock
    AuthenticationManager authMock;
    @Mock
    BankHistory histMock;
    @Mock
    InterestOperator intrestMock;

    AccountManager aM;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
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

    @AfterEach
    void tearDown() {
    }

    @Test
    void paymentIn() throws SQLException {
        // Set up test case
        User user = new User();
        user.setName("Alicja");
        int accountId  = 2;
        Account acc = new Account();
        acc.setAmmount(100);
        when(daoMock.findAccountById(accountId)).thenReturn(acc);
        when(daoMock.updateAccountState(any(Account.class))).thenReturn(true);
        //Run test
        boolean result = aM.paymentIn(user,122,"Payment",2);
        //Do test checks
        assertTrue(result);
        assertEquals(acc.getAmmount(),100+122, 0.001);
        verify(daoMock,times(1)).updateAccountState(any());
        verify(daoMock,times(1)).updateAccountState(acc);
        verify(histMock,times(1)).logOperation(any(Operation.class),eq(true));
    }
}