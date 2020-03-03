# Skester

Skester is a [Skript](https://github.com/SkriptLang/Skript) addon to create your own tests for your scripts.

## Documentations

[![SkriptHubViewTheDocs](http://skripthub.net/static/addon/ViewTheDocsButton.png)](http://skripthub.net/docs/?addon=Skester)

## Usage

```vb
command /check:
    trigger:
        it "should be the console which execute this command":
            assert equal sender to console with error message "Only console can execute this command!"
```

## Issues & Suggestions

If you have any issue or suggestion about Skester, please create [an issue](https://github.com/Olyno/skester/issues/new)