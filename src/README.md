MySMTPServer
=============
This program is to implement an SMTP server in Java that supports multi-threading and stores emails into local files. The program basically communicate with client with basic command: HELO, MAIL FROM:, RCPT TO, QUIT. All the commands **ignore the cases**

**COMPILATION**
-------------
Inside src directory, use *$ javac MySMTPServer.java* command to compile both files. After a successful compilation, there should be 2 more files ending with '.class' show up.

**RUN**
-------------
In command line, use *$ java MySMTPServer &* command to run the server.
Then, use *$ telnet 127.0.0.1 6013* command to connect to the server as a client.
-if connect to a server running on a remote machine, use *$ telnet <remoteMachineIP> 6013* command instead.

**TEST**
-------------
After successfully used *telnet* command, you should be able to see 'Welcome! *clientName*. Ready at *currentDate*' as response.
-Test *HELO* command by typing 'helo' in any cases, you should see 'Good day, *clientName* :-), I am *serverName*'

-Test *DATA* command before *MAIL* command. It says 'Sorry, I need a MAIL command first'

-Test *DATA* command after *MAIL* but before *RCPT* command. It says 'Sorry, i need a RCPT command first'

-Test *MAIL FROM:* command with 'mail from:*senderAddr*', it will show up '*senderAddr* sender received OK'

-Test *RCPT TO:* command with 'rcpt to:*receiverAddr*', it will say '*receiverAddr* user received OK'

-Test *DATA* command after above. It asks user to type the subject of this mail (can be also empty), then start typing body after 'Enter the mail - end with a '.' on a line' notification, then, press a sigle '.' to end the email and save it in to a directory called *emails* under src folder.

-Test any other undefined command. e.g. top, send, etc. The server would give 'I think you entered wrong command, please try again.'
-Test *QUIT* command. The server response:
'Closing connection ...
'Connection closed, bye bye.'
'Connection closed by foreign host.'

-After all the tests, kill the process.

**Contributor**
--------------
Jiaxi Li


