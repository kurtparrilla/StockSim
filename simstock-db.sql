CREATE TABLE Users(
  user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  wallet_balance DECIMAL(20, 2) NOT NULL DEFAULT 10000.00
    CHECK(wallet_balance >= 0),
  reserved_cash DECIMAL(20, 2) NOT NULL DEFAULT 0.00
    CHECK(reserved_cash >= 0)
);
CREATE TABLE Stocks(
  stock_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  stock_symbol VARCHAR(20) NOT NULL UNIQUE,
  stock_name VARCHAR(50) NOT NULL,
  reference_price DECIMAL(20, 2)
    CHECK(reference_price >= 0),
  price_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE Holdings(
  holding_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id INT NOT NULL,
  stock_id INT NOT NULL,
  quantity INT NOT NULL DEFAULT 0
    CHECK(quantity >= 0),
  reserved_quantity INT NOT NULL DEFAULT 0
    CHECK(reserved_quantity >= 0),
  average_buy_price DECIMAL(20, 2)
    CHECK(average_buy_price >= 0),
  CONSTRAINT fk_stocks
    FOREIGN KEY (stock_id)
    REFERENCES Stocks(stock_id),
  CONSTRAINT fk_users
    FOREIGN KEY (user_id)
    REFERENCES Users(user_id),
  CONSTRAINT unique_user_stock
    UNIQUE(user_id, stock_id),
  CONSTRAINT valid_reserved_quantity
    CHECK(reserved_quantity <= quantity)
);
CREATE TABLE Orders(
  order_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id INT NOT NULL,
  stock_id INT NOT NULL,
  order_type VARCHAR(10) NOT NULL
    CHECK(order_type IN ('BUY', 'SELL')),
  quantity INT
    CHECK(quantity > 0),
  remaining_quantity INT
    CHECK(remaining_quantity >= 0),
  limit_price DECIMAL(20, 2) NOT NULL
    CHECK(limit_price > 0),
  status VARCHAR(20) NOT NULL DEFAULT 'OPEN'
    CHECK(status IN ('OPEN', 'PARTIALLY_FILLED', 'FILLED', 'CANCELLED')),
  submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_users_order
    FOREIGN KEY (user_id)
    REFERENCES Users(user_id),
  CONSTRAINT fk_stocks_order
    FOREIGN KEY (stock_id)
    REFERENCES Stocks(stock_id),
  CONSTRAINT valid_remaining_quantity
    CHECK(remaining_quantity <= quantity)
);

CREATE TABLE Trades(
  trade_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  stock_id INT NOT NULL,
  buy_order_id INT NOT NULL,
  sell_order_id INT NOT NULL,
  quantity INT NOT NULL
    CHECK(quantity > 0),
  execution_price DECIMAL(20, 2) NOT NULL
    CHECK(execution_price > 0),
  executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_stocks_trades
    FOREIGN KEY (stock_id)
    REFERENCES Stocks(stock_id),
  CONSTRAINT fk_buy_orders
    FOREIGN KEY (buy_order_id)
    REFERENCES Orders(order_id),
  CONSTRAINT fk_sell_orders
    FOREIGN KEY (sell_order_id)
    REFERENCES Orders(order_id),
  CONSTRAINT different_orders
    CHECK(buy_order_id <> sell_order_id)
);
