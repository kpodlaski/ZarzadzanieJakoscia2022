package biz;

import db.dao.DAO;
import model.Account;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


class AccountManagerTest {
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

    @Test
    void paymentIn() throws SQLException {
        User user = new User();
        double ammount = 100;
        int accountId = 2;
        String desc = "AAAA";
        Account acc = mock(Account.class);
        when(mockDao.findAccountById(anyInt())).thenReturn(acc);
        when(mockDao.updateAccountState(acc)).thenReturn(true);
        when(acc.income(ammount)).thenReturn(true);
        boolean result = aM.paymentIn(user,ammount,desc,accountId);
        assertTrue(result);
        verify(mockDao, times(1)).findAccountById(anyInt());
        verify(mockDao, times(1)).updateAccountState(acc);
        verify(acc,times(1)).income(ammount);
        //assertEquals(200, acc.getAmmount());
        //TODO, TEST if logHistory had appropriate arguments
    }
}