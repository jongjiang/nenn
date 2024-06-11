package nenn.addons.account.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class AccountAccount {

    private static final Logger _logger = LogManager.getLogger(AccountAccount.class);

    private static final Pattern ACCOUNT_REGEX = Pattern.compile(
        "(?:(\\S*\\d+\\S*))?(.*)");
    private static final Pattern ACCOUNT_CODE_REGEX = Pattern.compile(
        "^[A-Za-z0-9.]+$");

    // Fields
    private String name;
    private ResCurrency currencyId;
    private ResCurrency companyCurrencyId;
    private String code;
    private boolean deprecated;
    private boolean used;
    private String accountType;
    private boolean includeInitialBalance;
    private String internalGroup;
    // private boolean hasUnreconciledEntries;
    private boolean reconcile;
    private List<AccountTax> taxIds;
    private String note;
    private ResCompany companyId;
    private List<AccountAccountTag> tagIds;
    private AccountGroup groupId;
    private AccountRoot rootId;
    private List<AccountJournal> allowedJournalIds;
    private BigDecimal openingDebit;
    private BigDecimal openingCredit;
    private BigDecimal openingBalance;
    private double currentBalance;
    private int relatedTaxesAmount;
    private boolean nonTrade;

    // Methods
    public void checkReconcile() {
        if (Arrays.asList("asset_receivable", "liability_payable").contains(accountType) && !reconcile) {
            throw new IllegalArgumentException(
                String.format(
                    "You cannot have a receivable/payable account that is not reconcilable. (account code: %s)",
                    code
                )
            );
        }
    }

    public void checkAccountTypeUniqueCurrentYearEarning() {
        // TODO: Implement this method
    }

    public void checkCode() {
        if (companyId.getRootId() != null) {
            // Check for duplicates in each root company
            List<AccountAccount> accounts = new ArrayList<>();
            // Group by company ID
            for (AccountAccount account : accounts) {
                if (account.companyId.getRootId().getId() == companyId.getRootId().getId()) {
                    accounts.add(account);
                }
            }
            // Group by code
            Map<String, List<AccountAccount>> accountsByCode = new HashMap<>();
            for (AccountAccount account : accounts) {
                accountsByCode.putIfAbsent(account.code, new ArrayList<>());
                accountsByCode.get(account.code).add(account);
            }
            // Check for duplicates
            for (List<AccountAccount> duplicateAccounts : accountsByCode.values()) {
                if (duplicateAccounts.size() > 1) {
                    throw new IllegalArgumentException(
                        String.format(
                            "The code of the account must be unique per company! (duplicates: %s)",
                            StringUtils.join(duplicateAccounts, ", ")
                        )
                    );
                }
            }
        }
    }

    public void checkReconcileConstraints() {
        if (internalGroup.equals("off_balance")) {
            if (reconcile) {
                throw new IllegalArgumentException("An Off-Balance account cannot be reconcilable.");
            }
            if (!taxIds.isEmpty()) {
                throw new IllegalArgumentException("An Off-Balance account cannot have taxes.");
            }
        }
    }

    public void checkAllowedJournalIds() {
        // TODO: Implement this method
    }

    public void checkJournalConsistency() {
        // TODO: Implement this method
    }

    public void checkCompanyConsistency() {
        // TODO: Implement this method
    }

    public void checkAccountTypeSalesPurchaseJournal() {
        // TODO: Implement this method
    }

    public void checkUsedAsJournalDefaultDebitCreditAccount() {
        // TODO: Implement this method
    }

    public void checkAccountCode() {
        Matcher matcher = ACCOUNT_CODE_REGEX.matcher(code);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                "The account code can only contain alphanumeric characters and dots."
            );
        }
    }

    public void checkAccountIsBankJournalBankAccount() {
        // TODO: Implement this method
    }

    public void computeAccountRoot() {
        if (code != null) {
            rootId = new AccountRoot(code);
        } else {
            rootId = null;
        }
    }

    public void computeAccountGroup() {
        // TODO: Implement this method
    }

    public void searchUsed(boolean value) {
        // TODO: Implement this method
    }

    public void computeUsed() {
        // TODO: Implement this method
    }

    public String searchNewAccountCode(ResCompany company, int digits, String prefix) {
        // TODO: Implement this method
    }

    public void computeCurrentBalance() {
        // TODO: Implement this method
    }

    public void computeRelatedTaxesAmount() {
        // TODO: Implement this method
    }

    public void computeOpeningDebitCredit() {
        openingDebit = BigDecimal.ZERO;
        openingCredit = BigDecimal.ZERO;
        openingBalance = BigDecimal.ZERO;
        // TODO: Implement this method
    }

    public void computeAccountType() {
        // TODO: Implement this method
    }

    public void computeIncludeInitialBalance() {
        if (accountType != null) {
            includeInitialBalance = !Arrays.asList(
                "income", "income_other", "expense", "expense_depreciation",
                "expense_direct_cost", "off_balance"
            ).contains(accountType);
        } else {
            includeInitialBalance = false;
        }
    }

    public void computeInternalGroup() {
        if (accountType != null) {
            if (accountType.equals("off_balance")) {
                internalGroup = "off_balance";
            } else {
                internalGroup = accountType.split("_")[0];
            }
        } else {
            internalGroup = null;
        }
    }

    public void computeReconcile() {
        if (accountType != null) {
            reconcile = Arrays.asList("asset_receivable", "liability_payable").contains(accountType);
        } else {
            reconcile = false;
        }
    }

    public void setOpeningDebit() {
        // TODO: Implement this method
    }

    public void setOpeningCredit() {
        // TODO: Implement this method
    }

    public void setOpeningBalance() {
        // TODO: Implement this method
    }

    public void setOpeningDebitCredit(BigDecimal amount, String field) {
        // TODO: Implement this method
    }

    public Map<String, Object> defaultGet(List<String> defaultFields) {
        // TODO: Implement this method
    }

    public List<Integer> getMostFrequentAccountsForPartner(Integer companyId, Integer partnerId, String moveType, boolean filterNeverUserAccounts, Integer limit) {
        // TODO: Implement this method
    }

    public Integer getMostFrequentAccountForPartner(Integer companyId, Integer partnerId, String moveType) {
        // TODO: Implement this method
    }

    public List<Integer> orderAccountsByFrequencyForPartner(Integer companyId, Integer partnerId, String moveType) {
        // TODO: Implement this method
    }

    public List<AccountAccount> nameSearch(String name, List<Object> domain, String operator, Integer limit, String order) {
        // TODO: Implement this method
    }

    public void onchangeAccountType() {
        if (internalGroup.equals("off_balance")) {
            taxIds.clear();
        }
    }

    public void onchangeName() {
        // TODO: Implement this method
    }

    public void computeDisplayName() {
        // TODO: Implement this method
    }

    public AccountAccount copy(Map<String, Object> defaultValues) {
        // TODO: Implement this method
    }

    public void copyTranslations(AccountAccount newAccount, List<String> excluded) {
        // TODO: Implement this method
    }

    public void loadPrecommitUpdateOpeningMove() {
        // TODO: Implement this method
    }

    public void toggleReconcileToTrue() {
        // TODO: Implement this method
    }

    public void toggleReconcileToFalse() {
        // TODO: Implement this method
    }

    public Integer nameCreate(String name) {
        // TODO: Implement this method
    }

    public List<AccountAccount> create(List<Map<String, Object>> valsList) {
        // TODO: Implement this method
    }

    public void write(Map<String, Object> vals) {
        // TODO: Implement this method
    }

    public void unlinkExceptContainsJournalItems() {
        // TODO: Implement this method
    }

    public void unlinkExceptAccountSetOnCustomer() {
        // TODO: Implement this method
    }

    public void unlinkExceptLinkedToFiscalPosition() {
        // TODO: Implement this method
    }

    public void unlinkExceptLinkedToTaxRepartitionLine() {
        // TODO: Implement this method
    }

    public Map<String, Object> actionOpenRelatedTaxes() {
        // TODO: Implement this method
    }

    public List<Map<String, Object>> getImportTemplates() {
        // TODO: Implement this method
    }

    public void mergeMethod(AccountAccount destination, AccountAccount source) {
        // TODO: Implement this method
    }

    // Constructor
    public AccountAccount(String name, ResCurrency currencyId, ResCompany companyId, String code, boolean deprecated,
                         String accountType, boolean reconcile, String note, List<AccountTax> taxIds,
                         List<AccountAccountTag> tagIds, AccountGroup groupId, List<AccountJournal> allowedJournalIds,
                         BigDecimal openingDebit, BigDecimal openingCredit, BigDecimal openingBalance,
                         boolean nonTrade) {
        this.name = name;
        this.currencyId = currencyId;
        this.companyId = companyId;
        this.code = code;
        this.deprecated = deprecated;
        this.accountType = accountType;
        this.reconcile = reconcile;
        this.note = note;
        this.taxIds = taxIds;
        this.tagIds = tagIds;
        this.groupId = groupId;
        this.allowedJournalIds = allowedJournalIds;
        this.openingDebit = openingDebit;
        this.openingCredit = openingCredit;
        this.openingBalance = openingBalance;
        this.nonTrade = nonTrade;
    }

    // Getters
    public String getName() {
        return name;
    }

    public ResCurrency getCurrencyId() {
        return currencyId;
    }

    public ResCurrency getCompanyCurrencyId() {
        return companyCurrencyId;
    }

    public String getCode() {
        return code;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public boolean isUsed() {
        return used;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isIncludeInitialBalance() {
        return includeInitialBalance;
    }

    public String getInternalGroup() {
        return internalGroup;
    }

    public boolean isReconcile() {
        return reconcile;
    }

    public List<AccountTax> getTaxIds() {
        return taxIds;
    }

    public String getNote() {
        return note;
    }

    public ResCompany getCompanyId() {
        return companyId;
    }

    public List<AccountAccountTag> getTagIds() {
        return tagIds;
    }

    public AccountGroup getGroupId() {
        return groupId;
    }

    public AccountRoot getRootId() {
        return rootId;
    }

    public List<AccountJournal> getAllowedJournalIds() {
        return allowedJournalIds;
    }

    public BigDecimal getOpeningDebit() {
        return openingDebit;
    }

    public BigDecimal getOpeningCredit() {
        return openingCredit;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public int getRelatedTaxesAmount() {
        return relatedTaxesAmount;
    }

    public boolean isNonTrade() {
        return nonTrade;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCurrencyId(ResCurrency currencyId) {
        this.currencyId = currencyId;
    }

    public void setCompanyCurrencyId(ResCurrency companyCurrencyId) {
        this.companyCurrencyId = companyCurrencyId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setIncludeInitialBalance(boolean includeInitialBalance) {
        this.includeInitialBalance = includeInitialBalance;
    }

    public void setInternalGroup(String internalGroup) {
        this.internalGroup = internalGroup;
    }

    public void setReconcile(boolean reconcile) {
        this.reconcile = reconcile;
    }

    public void setTaxIds(List<AccountTax> taxIds) {
        this.taxIds = taxIds;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCompanyId(ResCompany companyId) {
        this.companyId = companyId;
    }

    public void setTagIds(List<AccountAccountTag> tagIds) {
        this.tagIds = tagIds;
    }

    public void setGroupId(AccountGroup groupId) {
        this.groupId = groupId;
    }

    public void setRootId(AccountRoot rootId) {
        this.rootId = rootId;
    }

    public void setAllowedJournalIds(List<AccountJournal> allowedJournalIds) {
        this.allowedJournalIds = allowedJournalIds;
    }

    public void setOpeningDebit(BigDecimal openingDebit) {
        this.openingDebit = openingDebit;
    }

    public void setOpeningCredit(BigDecimal openingCredit) {
        this.openingCredit = openingCredit;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public void setRelatedTaxesAmount(int relatedTaxesAmount) {
        this.relatedTaxesAmount = relatedTaxesAmount;
    }

    public void setNonTrade(boolean nonTrade) {
        this.nonTrade = nonTrade;
    }
}

// Supporting classes

class ResCurrency {
    private Integer id;

    public ResCurrency(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

class ResCompany {
    private Integer id;
    private AccountRoot rootId;
    private AccountAccount accountJournalPaymentDebitAccount;
    private AccountAccount accountJournalPaymentCreditAccount;

    public ResCompany(Integer id, AccountRoot rootId, AccountAccount accountJournalPaymentDebitAccount,
                     AccountAccount accountJournalPaymentCreditAccount) {
        this.id = id;
        this.rootId = rootId;
        this.accountJournalPaymentDebitAccount = accountJournalPaymentDebitAccount;
        this.accountJournalPaymentCreditAccount = accountJournalPaymentCreditAccount;
    }

    public Integer getId() {
        return id;
    }

    public AccountRoot getRootId() {
        return rootId;
    }

    public AccountAccount getAccountJournalPaymentDebitAccount() {
        return accountJournalPaymentDebitAccount;
    }

    public AccountAccount getAccountJournalPaymentCreditAccount() {
        return accountJournalPaymentCreditAccount;
    }
}

class AccountTax {
    private Integer id;

    public AccountTax(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

class AccountAccountTag {
    private Integer id;

    public AccountAccountTag(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

class AccountGroup {
    private Integer id;
    private AccountGroup parentId;
    private String name;
    private String codePrefixStart;
    private String codePrefixEnd;
    private ResCompany companyId;

    public AccountGroup(Integer id, AccountGroup parentId, String name, String codePrefixStart,
                       String codePrefixEnd, ResCompany companyId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.codePrefixStart = codePrefixStart;
        this.codePrefixEnd = codePrefixEnd;
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public AccountGroup getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getCodePrefixStart() {
        return codePrefixStart;
    }

    public String getCodePrefixEnd() {
        return codePrefixEnd;
    }

    public ResCompany getCompanyId() {
        return companyId;
    }
}

class AccountRoot {
    private Integer id;
    private String name;
    private AccountRoot parentId;
    private ResCompany companyId;

    public AccountRoot(String code) {
        this.id = (int) (code.charAt(0) * 1000 + code.charAt(1));
        this.name = code.substring(0, 2);
        this.parentId = code.length() == 2 ? new AccountRoot(code.substring(0, 1)) : null;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AccountRoot getParentId() {
        return parentId;
    }

    public ResCompany getCompanyId() {
        return companyId;
    }
}

class AccountJournal {
    private Integer id;
    private String type;
    private AccountAccount defaultAccountId;
    private AccountAccount suspenseAccountId;
    private ResCurrency currencyId;

    public AccountJournal(Integer id, String type, AccountAccount defaultAccountId,
                         AccountAccount suspenseAccountId, ResCurrency currencyId) {
        this.id = id;
        this.type = type;
        this.defaultAccountId = defaultAccountId;
        this.suspenseAccountId = suspenseAccountId;
        this.currencyId = currencyId;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public AccountAccount getDefaultAccountId() {
        return defaultAccountId;
    }

    public AccountAccount getSuspenseAccountId() {
        return suspenseAccountId;
    }

    public ResCurrency getCurrencyId() {
        return currencyId;
    }
}