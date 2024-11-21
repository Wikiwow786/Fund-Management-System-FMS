CREATE TYPE status_enum AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TABLE fms.bank (
    bank_id SERIAL PRIMARY KEY,
    bank_name VARCHAR(100) NOT NULL,
    balance NUMERIC(15, 2) DEFAULT 0.00,
    balance_limit DECIMAL(18, 2),
    status status_enum NOT NULL DEFAULT 'ACTIVE',
    remarks TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INDEXES FOR Bank:
CREATE INDEX idx_bank_name on fms.bank(bank_name);
CREATE INDEX idx_status on fms.bank(status);
CREATE INDEX idx_created_at on fms.bank(created_at);
CREATE INDEX idx_updated_at on fms.bank(updated_at);

CREATE TABLE fms.customer (
    customer_id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    bank_id INT REFERENCES fms.bank(bank_id) ON DELETE SET NULL,
    balance NUMERIC(15, 2) DEFAULT 0.00,
    fund_in_fee_pct DECIMAL(5, 2),
    fund_out_fee_pct DECIMAL(5, 2),
    fund_in_fee_commission_pct DECIMAL(5, 2),
    fund_out_fee_commission_pct DECIMAL(5, 2),
    middleman_id BIGINT,
    remarks TEXT,
    status status_enum NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INDEXES FOR CUSTOMER:
CREATE INDEX idx_customer_name on fms.customer(customer_name);
CREATE INDEX idx_customer_status on fms.customer(status);
CREATE INDEX idx_customer_start_date on fms.customer(start_date);
CREATE INDEX idx_customer_end_date on fms.customer(end_date);

CREATE TABLE fms.transaction (
    transaction_id SERIAL PRIMARY KEY,
    transaction_type VARCHAR(50) NOT NULL CHECK (transaction_type IN ('FUND_IN', 'FUND_OUT')),
    bank_id INT REFERENCES fms.bank(bank_id) ON DELETE CASCADE,
    customer_id INT REFERENCES fms.customer(customer_id) ON DELETE SET NULL,
    amount NUMERIC(15, 2) NOT NULL,
    transaction_date DATE NOT NULL,
    transaction_time TIME NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('COMPLETED', 'VOIDED')),
    remark TEXT,
    external_id bigint,
    created_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    updated_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INDEXES FOR TRANSACTION:
CREATE INDEX idx_transaction_customer_id on fms.transaction(customer_id);
CREATE INDEX idx_transaction_bank_id on fms.transaction(bank_id);
CREATE INDEX idx_transaction_date on fms.transaction(transaction_date);
CREATE INDEX idx_transaction_time on fms.transaction(transaction_time);
CREATE INDEX idx_transaction_amount on fms.transaction(amount);
CREATE INDEX idx_transaction_transaction_type on fms.transaction(transaction_type);
CREATE INDEX idx_transaction_created_by on fms.transaction(created_by);


CREATE TABLE fms.manual_entry (
    manual_entry_id SERIAL PRIMARY KEY,
    bank_id INT REFERENCES fms.bank(bank_id) ON DELETE CASCADE,
    transaction_id INT REFERENCES fms.transaction(transaction_id) ON DELETE CASCADE,
    entry_date DATE NOT NULL,
    entry_time TIME NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    entry_type VARCHAR(50) NOT NULL CHECK (entry_type IN ('bank_interest', 'expense', 'others')),
    status VARCHAR(50) NOT NULL CHECK (status IN ('VALID', 'VOIDED')),
    remark TEXT,
    created_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    updated_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INDEXES ON ManualEntry:
CREATE INDEX idx_manual_entry_bank_id on fms.manual_entry(bank_id);
CREATE INDEX idx_manual_entry_entry_date on fms.manual_entry(entry_date);
CREATE INDEX idx_manual_entry_entry_time on fms.manual_entry(entry_time);
CREATE INDEX idx_manual_entry_entry_type on fms.manual_entry(entry_type);
CREATE INDEX idx_manual_entry_created_by on fms.manual_entry(created_by);


CREATE TABLE fms.unclaimed_amount (
    unclaimed_id SERIAL PRIMARY KEY,
    bank_id INT REFERENCES fms.bank(bank_id) ON DELETE CASCADE,
    claim_as_transaction_id INT REFERENCES fms.transaction(transaction_id) ON DELETE CASCADE,
    amount NUMERIC(15, 2) NOT NULL,
    transaction_date DATE NOT NULL,
    transaction_time TIME NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('UNCLAIMED', 'CLAIMED', 'VOIDED')),
    remark TEXT,
    void_remark TEXT,
    created_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    updated_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    claimed_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);


INDEXES FOR UnclaimedAmount:
CREATE INDEX idx_unclaimed_amount_bank_id on fms.unclaimed_amount(bank_id);
CREATE INDEX idx_unclaimed_amount_amount on fms.unclaimed_amount(amount);
CREATE INDEX idx_unclaimed_amount_created_by on fms.unclaimed_amount(created_by);
CREATE INDEX idx_unclaimed_amount_updated_by on fms.unclaimed_amount(updated_by);
CREATE INDEX idx_unclaimed_amount_status on fms.unclaimed_amount(status);

-- Balance Transfer Table
CREATE TABLE fms.balance_transfer (
    transfer_id BIGINT NOT NULL CONSTRAINT pk_balance_transfer PRIMARY KEY,
    transfer_date DATE NOT NULL,
    transfer_time TIME NOT NULL,
    amount DECIMAL(18, 2) NOT NULL,
    source_customer_id BIGINT REFERENCES fms.customer ON DELETE CASCADE,
    target_customer_id BIGINT REFERENCES fms.customer ON DELETE CASCADE,
    source_bank_id BIGINT REFERENCES fms.bank ON DELETE CASCADE,
    target_bank_id BIGINT REFERENCES fms.bank ON DELETE CASCADE,
    remarks TEXT,
    created_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INDEXES FOR Balance Transfer Table:
CREATE INDEX idx_balance_transfer_transfer_date ON fms.balance_transfer (transfer_date);
CREATE INDEX idx_balance_transfer_transfer_time ON fms.balance_transfer (transfer_time);
CREATE INDEX idx_balance_transfer_source_customer_id ON fms.balance_transfer (source_customer_id);
CREATE INDEX idx_balance_transfer_target_customer_id ON fms.balance_transfer (target_customer_id);
CREATE INDEX idx_balance_transfer_source_bank_id ON fms.balance_transfer (source_bank_id);
CREATE INDEX idx_balance_transfer_target_bank_id ON fms.balance_transfer (target_bank_id);


-- Balance Transaction Table

CREATE TABLE fms.balance_transfer_transaction(



-- Revenue Account Table
CREATE TABLE fms.revenue_account (
    revenue_account_id BIGINT NOT NULL CONSTRAINT pk_revenue_account PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    balance DECIMAL(18, 2) DEFAULT 0,
    status status_enum NOT NULL DEFAULT 'ACTIVE',
    start_date DATE,
    remarks TEXT
);

INDEXES FOR REVENUE ACCOUNT TABLE:
CREATE INDEX idx_account_name on fms.revenue_account(account_name);
CREATE INDEX idx_revenue_account_status on fms.revenue_account(status);
CREATE INDEX idx_revenue_account_start_date on fms.revenue_account(start_date);
CREATE INDEX idx_revenue_account_end_date on fms.revenue_account(end_date);

-- User Table
CREATE TABLE fms.user (
    user_id BIGINT NOT NULL CONSTRAINT pk_user PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT REFERENCES fms.role ON DELETE SET NULL,
    status status_enum NOT NULL DEFAULT 'ACTIVE',
    created_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    updated_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INDEXES FOR User Table:
CREATE INDEX idx_name on fms.user(user_name);
CREATE INDEX idx_role_id on fms.user(role_id);


-- Role Table
CREATE  TABLE fms.role (
    role_id BIGINT NOT NULL CONSTRAINT pk_role PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL,
    status status_enum NOT NULL DEFAULT 'ACTIVE'
);

INDEXES FOR Role Table:
CREATE INDEX idx_role_name on fms.role(role_name)


-- Activity Log Table
CREATE TABLE fms.activity_log (
    log_id BIGINT NOT NULL CONSTRAINT pk_activity_log PRIMARY KEY,
    user_id BIGINT REFERENCES fms.user ON DELETE CASCADE,
    action VARCHAR(255),
    date TIMESTAMP,
    remarks TEXT
);

INDEXES FOR ActivityLog Table:
CREATE INDEX idx_user_id on fms.activity_log(user_id);
CREATE INDEX idx_date on fms.activity_log(date);

-- Permission Table
CREATE TABLE fms.permission (
    permission_id BIGINT NOT NULL CONSTRAINT pk_permission PRIMARY KEY,
    permission_name VARCHAR(255) UNIQUE NOT NULL
);

INDEXES FOR Permission Table:
CREATE INDEX idx_permission_name on fms.permission(permission_name)

-- RolePermission Table
CREATE TABLE fms.role_permission (
    role_permission_id BIGINT PRIMARY KEY,       
    role_id BIGINT NOT NULL,                     
    permission_id BIGINT NOT NULL,               
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES fms.role ON DELETE CASCADE,
    CONSTRAINT fk_permission FOREIGN KEY (permission_id) REFERENCES fms.permission ON DELETE CASCADE,
    UNIQUE (role_id, permission_id)              -- Ensures each role-permission pair is unique
);

CREATE INDEX idx_role_permission_role_id on fms.role_permission(role_id);
CREATE INDEX idx_role_permission_permission_id on fms.role_permission(permission_id);

CREATE TABLE fms.middleman_payout (
    payout_id BIGINT SERIAL PRIMARY KEY, -- Unique identifier for each payout
    revenue_account_id BIGINT REFERENCES fms.revenue_account(revenue_account_id) ON DELETE CASCADE, -- Links to the revenue_account table
    payout_amount DECIMAL(18, 2) NOT NULL, -- Amount to be paid out
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL, -- Timestamp when the payout was created
    created_by INT REFERENCES fms.user(user_id) ON DELETE SET NULL --ID of the user/system who processed the payout
);
