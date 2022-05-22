package biz;

import db.dao.DAO;
import db.dao.NoSuchAccount;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AccountManagerTest {
    AccountManager aM;
    @Mock
    DAO mockDao;
    @Mock
    BankHistory mockHistory;
    @Mock
    AuthenticationManager mockAuth;
    @Mock
    InterestOperator mockInterestOperator;

    @BeforeEach
    void setUp() {
        mockDao = mock(DAO.class);
        mockHistory = mock(BankHistory.class);
        mockAuth = mock(AuthenticationManager.class);
        mockInterestOperator = mock(InterestOperator.class);
        aM = new AccountManager();
        aM.history = mockHistory;
        aM.auth = mockAuth;
        aM.interestOperator = mockInterestOperator;
        try {
            Field daoField = AccountManager.class.getDeclaredField("dao");
            daoField.setAccessible(true);
            daoField.set(aM,mockDao);
            daoField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void paymentInAllOk() throws NoSuchAccount, SQLException {
        User user = new User();
        double ammount = 100;
        String desc = "AAA";
        int accId = 2;
        Account acc = mock(Account.class);
        //when(mockDao.findAccountById(accId)).thenReturn(acc);
        when(mockDao.findAccountById(anyInt())).thenReturn(acc);
        when(mockDao.updateAccountState(acc)).thenReturn(true);
        boolean result = aM.paymentIn(user,ammount,desc,accId);
        verify(acc, times(1)).income(100);
        verifyNoMoreInteractions(acc);
        verify(mockDao, times(1)).findAccountById(accId);
        verify(mockDao, times(1)).updateAccountState(acc);
        verifyNoMoreInteractions(mockDao);
        verify(mockHistory,times(1))
                .logOperation(any(Operation.class),eq(true));

        assertTrue(result);
    }

    @Test
    void paymentInDaoExcetion1() throws NoSuchAccount, SQLException {
        when(mockDao.findAccountById(anyInt())).thenThrow(SQLException.class);
        assertThrows(SQLException.class,
                ()->{aM.paymentIn(new User(),12,"as",12);});
    }
    @Test
    void paymentInDaoExcetion2() throws NoSuchAccount, SQLException {
        when(mockDao.findAccountById(anyInt())).thenReturn(new Account());
        when(mockDao.updateAccountState(any(Account.class))).thenThrow(SQLException.class);
        assertThrows(SQLException.class,
                ()->{aM.paymentIn(new User(),12,"as",12);});
    }

    @Test
    void paymentInAccountNotExist() throws NoSuchAccount, SQLException {
        when(mockDao.findAccountById(anyInt())).thenReturn(null);
        User user = new User();
        double ammount = 100;
        String desc = "AAA";
        int accId = 2;
        assertThrows(NoSuchAccount.class,
                ()->{aM.paymentIn(user,ammount,desc,accId);});
        //TODO heck if opperation is logged
    }
}