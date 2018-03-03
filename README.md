# ksplice not reporting correct pid to `jnr-possix`

The original issue was encountered running this jython code on a Oracle Linux 7.2 server with ksplice

```sh
$ jython -c "import os; print os.getpid()"
Traceback (most recent call last):
  File "<string>", line 1, in <module>
AttributeError: 'module' object has no attribute 'getpid'
```

A few `strace`s and a simplified error example later, this is what we found so far.

Run the simplified test:

```sh
$ strace java -jar ksplice-pid-problem-1.0.0.jar
```


Inspecting the strace log, we can see in [`trace-with-ksplice.log#L97`](https://github.com/bertramn/ksplice-pid-problem/blob/b87c6de315f7043e25888318e77b995d7c40cb46/trace/trace-with-ksplice.log#L97) that the thread id is read correctly:

```
set_tid_address(0x7efdef557a90)         = 2079 <-- thread ID is known
```

but [`trace-with-ksplice.log#L159`](https://github.com/bertramn/ksplice-pid-problem/blob/b87c6de315f7043e25888318e77b995d7c40cb46/trace/trace-with-ksplice.log#L159) shows the Java process returning 0

```
the value returned by the Java executable however is 0
```


Running this on the same server without ksplice will work as expected [`trace-no-ksplice.log#L129`](https://github.com/bertramn/ksplice-pid-problem/blob/b87c6de315f7043e25888318e77b995d7c40cb46/trace/trace-no-ksplice.log#L129).

In addition to the pid issue, running Jython with ksplice will result in filesystem issues whereby the JVM fails to obtain fil handles from the underlying OS whilst they are available. These failures occur for instance during installation of Jython and the `strace` indicates ksplice also in the middle of this failure.
