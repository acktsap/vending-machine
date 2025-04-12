# Vending Machine

## Design

```text
== Responsibility ==

Input

- Take drink
- Take card
- Take cash
- Cancel Button

Output

- Show output

Payment
Select target
Check if payment is possible
Get payment back

== Model ==

- PaymentType
- Drink

Possible Inputs

- Insert Card
- Insert Coin
- Choose drink

Internal State

- Coin Exchange
- Drink Stock

State

- InitState
- CashTakenState
- CardTakenState
```