Payments UI tests
------------------

#Install needed tools

1. Install chrome driver on your system.

###Running from gradle:

gradle -Dtag="@MYTESTTAG" test
gradle -Dtag="@MYTESTTAG,@MYTESTTAG2" test

##Running on different environment:

```gradle test -Dtags=@UIPAGENAVIGATION -DtestEnvironment=localhost```

```gradle test -Dtags=@UIPAGENAVIGATION -DtestEnvironment=snapshot```

```gradle test -Dtags=@UIPAGENAVIGATION -DtestEnvironment=development```

```gradle test -Dtags=@UIPAGENAVIGATION -DtestEnvironment=stage```

```gradle test -Dtags=@UIPAGENAVIGATION -DtestEnvironment=production```
```gradle test -DtestEnvironment=production```
```gradle test -Dtags=@PRODUCTIONSAFE,@MYTAG -DtestEnvironment=production```
These will not work as they don't exclusively have the @PRODUCTIONSAFE tag.

```gradle test -Dtags=@PRODUCTIONSAFE -DtestEnvironment=production```

##Turning on screenshot trails (off by default)
These will give u a trail of screenshots before the failed test, rather than the screenshot at the failure point itself.
```gradle test -Dtags=@UIPAGENAVIGATION -DscreenshotTrail=true```
