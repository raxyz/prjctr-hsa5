### Pre requisits 

1. installed docker

## Results

Full description you can find in separate files:
- [Postgres](postgres.md)
- [Percona](percona.md)

### Postgres Results

|  | Dirty reads | Lost updates | Non-repeatable reads | Phantom reads |
|---|---|---|---|---|
| **Read Uncommitted**  | not supported | not supported | not supported | not supported |
| **Read Committed**    | not possible | not possible | possible | possible |
| **Repeatable Read**   | not possible | not possible | not possible | not possible |
| **Serializable**      | not possible | not possible | not possible | not possible |

### Percona Results

|  | Dirty reads | Lost updates | Non-repeatable reads | Phantom reads |
|---|---|---|---|---|
| **Read Uncommitted**  | possible | not possible | possible | possible |
| **Read Committed**    | not possible | not possible | possible | possible |
| **Repeatable Read**   | not possible | not possible | not possible | ~~not possible~~ possible |
| **Serializable**      | not possible | not possible | not possible | not possible |
