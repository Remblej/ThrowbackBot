# ThrowbackBot

ThrowbackBot is a Discord bot written in Kotlin that allows users to revisit memorable moments of the server. Bot executes "throwbacks" - it re-sends one of the older messages from given channel, at random.

## Features

- Execute a one-time throwback.
- Enable or disable automatic daily throwbacks.

## Commands

| Command                    | Description                             |
|----------------------------|-----------------------------------------|
| `/throwback`               | Executes one throwback.                 |
| `/enable_daily_throwback`  | Enables automatic throwback once a day. |
| `/disable_daily_throwback` | Disables automatic daily throwback.     |

## Getting Started

### Prerequisites

- [JDK 21 or later](https://adoptopenjdk.net/)
- A Discord bot token. You can get one by creating a bot on the [Discord Developer Portal](https://discord.com/developers/applications).
- Environment variable `THROWBACK_BOT_TOKEN` with your token

### Build the project

 ```sh
 ./gradlew build
 ```

### Run the bot

```sh
./gradlew run
```

## Usage

To use ThrowbackBot, invite it to your server and use the following commands:

- `/throwback`: Executes one throwback.
- `/enable_daily_throwback`: Enables automatic throwback once a day.
- `/disable_daily_throwback`: Disables automatic daily throwback.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes you would like to make.

## License

This project is licensed under the MIT License.

