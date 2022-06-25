package lib.ieris19.util.economy;

import lib.ieris19.util.economy.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static lib.ieris19.util.economy.Money.*;

@DisplayName ("Economy Library Test") class EconomyTest {
	private Money account;
	private Money wallet;

	private static final double INITIAL_VALUE = 10D;

	@BeforeEach void setUp() {
		account = new Money(INITIAL_VALUE);
		wallet = new Money(INITIAL_VALUE);
	}

	@Nested @DisplayName ("Money Unit Tests") class MoneyTest {
		@Nested @DisplayName ("Setter") class SetterTest {
			@Nested @DisplayName ("Values outside limits") class OutOfBoundsTest {
				@Test @DisplayName ("Overflow MAX") void maxOverflow() {
					assertThrows(IllegalArgumentException.class, () -> account.setAmount(Long.MAX_VALUE));
				}

				@Test @DisplayName ("Overflow MIN") void minOverflow() {
					assertThrows(IllegalArgumentException.class, () -> account.setAmount(Long.MIN_VALUE));
				}
			}

			@Nested @DisplayName ("Limit Values") class EdgeValueTests {
				@Test @DisplayName ("Max value") void minLimit() {
					assertDoesNotThrow(() -> account.setAmount(MAX_MONEY));
				}

				@Test @DisplayName ("Min value") void maxLimit() {
					assertDoesNotThrow(() -> account.setAmount(MIN_MONEY));
				}
			}

			@Nested @DisplayName ("Regular Values") class NormalValueTest {
				@Test @DisplayName ("Positive") void positiveInRange() {
					assertDoesNotThrow(() -> account.setAmount(new Random().nextDouble(0, MAX_MONEY)));
				}

				@Test @DisplayName ("Negative Integer within range") void negativeInRange() {
					assertDoesNotThrow(() -> account.setAmount(new Random().nextDouble(MIN_MONEY, 0)));
				}
			}

			@Nested @DisplayName ("Rounds the value correctly") class RoundingValueTest {
				double trueValue;

				@Test @DisplayName ("Rounds down under 0.005") void roundDown() {
					trueValue = 100.2545;
					account.setAmount(trueValue);
					assertEquals(100.25, account.getAmount());
					assertNotEquals(trueValue, account.getAmount());
				}

				@Test @DisplayName ("Rounds up at exactly 0.005") void roundUpEquals() {
					trueValue = (-100.255);
					account.setAmount(trueValue);
					assertEquals(-100.26, account.getAmount());
					assertNotEquals(trueValue, account.getAmount());
				}

				@Test @DisplayName ("Rounds up above 0.005") void roundUp() {
					trueValue = (100.2599);
					account.setAmount(trueValue);
					assertEquals(100.26, account.getAmount());
					assertNotEquals(trueValue, account.getAmount());
				}
			}
		}

		@Nested @DisplayName ("Tests related to addition") class AdditionTest {
			@Nested @DisplayName ("Throws an exception for negative or negligible amounts") class SmallAdditionsTest {
				@Test @DisplayName ("Negligible amount rounded down") void addVerySmall() {
					assertThrows(IllegalArgumentException.class, () -> account.addMoney(0.001));
				}

				@Test @DisplayName ("Add negative") void addNegative() {
					assertThrows(IllegalArgumentException.class, () -> account.addMoney(-140));
				}

				@Test @DisplayName ("Negligible amount rounded up") void addSmall() {
					assertDoesNotThrow(() -> account.addMoney(0.008));
				}
			}

			@Test @DisplayName ("Correctly adds expected amounts") void simpleAdditions() {
				account.addMoney(5);
				assertEquals((INITIAL_VALUE + 5), account.getAmount(),
										 "Added 5 to the account 5, result is 10");
			}

			@Nested @DisplayName ("Doesn't allow for overflow of the number above max amount") class BigAdditionsTest {
				@Test @DisplayName ("Adding exactly to max amount") void exactMax() {
					account.setAmount(999999995);
					account.addMoney(5);
					assertEquals(MAX_MONEY, account.getAmount());
				}

				@Test @DisplayName ("Adding way over the max") void bigMax() {
					account.setAmount(MAX_MONEY);
					account.addMoney(Long.MAX_VALUE + MAX_MONEY);
					assertEquals(MAX_MONEY, account.getAmount());
				}
			}
		}

		@Nested @DisplayName ("Tests related to subtraction") class SubtractionTest {
			@Nested @DisplayName ("Throws an exception for negative or negligible amounts") class SmallSubtractionTest {
				@Test @DisplayName ("Subtract too little") void minusZero() {
					assertThrows(IllegalArgumentException.class, () -> account.subtractMoney(0.001, false));
				}

				@Test @DisplayName ("Can't subtract negative numbers") void minusNegative() {
					assertThrows(IllegalArgumentException.class, () -> account.subtractMoney(-140, false));
				}

				@Test @DisplayName ("Subtracts small numbers correctly rounded") void minusRound() {
					assertDoesNotThrow(() -> account.subtractMoney(0.008, false));
				}

				@Test @DisplayName ("Subtract too little even if canOwe()") void minusZeroOwe() {
					assertThrows(IllegalArgumentException.class, () -> account.subtractMoney(0.001, true));
				}

				@Test @DisplayName ("Can't subtract negative numbers even if canOwe()") void minusNegativeOwe() {
					assertThrows(IllegalArgumentException.class, () -> account.subtractMoney(-140, true));
				}

				@Test @DisplayName ("Subtracts small numbers correctly rounded even if canOwe()") void minusRoundOwe() {
					assertDoesNotThrow(() -> account.subtractMoney(0.008, true));
				}
			}

			@Nested @DisplayName ("Correctly subtracts amounts") class SubtractionLogicTest {
				@Test @DisplayName ("Not owing returns correct value") void minusNotOwe() {
					account.subtractMoney(5, false);
					assertEquals((INITIAL_VALUE - 5), account.getAmount());
				}

				@Test @DisplayName ("Owing returns the correct value") void minusOwe() {
					account.subtractMoney(10, true);
					assertEquals((INITIAL_VALUE - 10), account.getAmount());
				}
			}

			@Test @DisplayName ("Doesn't allow for overflow") void subtractionOverflowTest() {
				account.subtractMoney(Long.MAX_VALUE, true);
				assertEquals(MIN_MONEY, account.getAmount());
			}

			@Nested @DisplayName ("Correctly flags insufficient funds") class SubtractionInsufficientFundsTest {
				@Test @DisplayName ("Subtract small amount above available") void aboveBudgetSlight() {
					assertThrows(IllegalArgumentException.class,
											 () -> account.subtractMoney(INITIAL_VALUE + 0.1, false));
				}

				@Test @DisplayName ("Subtract big amount above available") void aboveBudget() {
					assertThrows(IllegalArgumentException.class,
											 () -> account.subtractMoney(100 * INITIAL_VALUE, false));
				}

				@Test @DisplayName ("Subtract exactly to 0") void exactlyBudget() {
					assertDoesNotThrow(() -> account.subtractMoney(INITIAL_VALUE, false));
				}
			}
		}

		@Nested @DisplayName ("Tests related to transfers") class TransferTest {
			@Nested @DisplayName ("Inherits exceptions from addition and subtraction") class ExceptionInheritanceTest {
				@Test @DisplayName ("Transfer too little money") void lowAmountException() {
					assertThrows(IllegalArgumentException.class, () -> transfer(wallet, account, 0.001));
				}

				@Test @DisplayName ("Insufficient funds") void insufficientException() {
					assertThrows(IllegalArgumentException.class,
											 () -> transfer(wallet, account, INITIAL_VALUE + 1));
				}

				@Test @DisplayName ("Transfer of negative funds") void negativeValueException() {
					assertThrows(IllegalArgumentException.class, () -> transfer(wallet, account, -500));
				}
			}

			@Test @DisplayName ("Transfer math is correct") void correctTransfer() {
				transfer(wallet, account, 5.2558);
				assertEquals(INITIAL_VALUE - 5.26, wallet.getAmount());
				assertEquals(INITIAL_VALUE + 5.26, account.getAmount());
				transfer(account, wallet, 5.26);
				assertEquals(INITIAL_VALUE, wallet.getAmount());
				assertEquals(INITIAL_VALUE, account.getAmount());
			}
		}
	}
}