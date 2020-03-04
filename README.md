# Skester

Skester is a [Skript](https://github.com/SkriptLang/Skript) addon to create your own tests for your scripts.

## Documentations

[![SkriptHubViewTheDocs](http://skripthub.net/static/addon/ViewTheDocsButton.png)](http://skripthub.net/docs/?addon=Skester)

## Usage

**Write tests:**

```vb
command test [<text>]:
    trigger:
        it "checks the sender":
            assert if sender is player with message "The sender should be a player"
            it "checks permissions":
                assert if player is op with message "The player should be OP!"
        it "checks argument":
            assert not null arg-1 with message "First argument should not be null"
            assert equals arg-1 to "this" with message "First argument should be ""this"""
```

**And get results:**

![Result of the code below](https://i.imgur.com/B2snAB3.png)

## Issues & Suggestions

If you have any issue or suggestion about Skester, please create [an issue](https://github.com/Olyno/skester/issues/new)