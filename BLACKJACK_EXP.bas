10 PRINT "DO YOU WANT INSTRUCTIONS (IF SO TYPE I)"
15 INPUT K$
25 IF UPPER$(K$) <> "I" THEN GOTO GAME_START
30 PRINT
35 PRINT "THIS IS A GAME OF BLACKJACK. LAS VEGAS STYLE."
40 PRINT "HERE ARE THE RULES OF THE HOUSE. THE DEALER"
45 PRINT "MUST HIT ON 16 OR LESS AND MUST STAY ON 17 OR MORE."
50 PRINT "YOU MAY SPLIT TWO CARDS IF THEY ARE THE SAME AND PLAY ONE HAND WITH EACH OF THEM."
55 PRINT "ALSO, YOU MAY DOUBLE YOUR BET AND RECEIVE EXACTLY ONE MORE CARD ANY TIME ON YOUR FIRST HIT."
60 PRINT "THE TYPING INSTRUCTIONS ARE: 0 - NO HIT, 1 - HIT, 2 - DOUBLE, AND 3 - SPLIT A PAIR."
65 GOTO GAME_START

GAME_START:

100 PRINT "WHEN THE DEALER HAS AN EXPOSED ACE, HE WILL ASK"
105 PRINT "YOU FOR AN INSURANCE BET. AN INSURANCE BET WILL"
110 PRINT "RISK HALF YOUR BET FOR AN AMOUNT EQUAL TO YOUR BET."
115 PRINT "IF YOU WIN, YOU WIN IF THE DEALER HAS A BLACKJACK,"
120 PRINT "AND LOSE IF HE DOESN'T. THE HOUSE LIMIT IS $500.00."
125 PRINT "GOOD LUCK. OH, BY THE WAY, THE DEALER IS NOTED FOR"
130 PRINT "DEALING OFF THE BOTTOM OF THE DECK. WATCH HIM VERY"
135 PRINT "CLOSELY. HERE HE IS NOW."
140 PRINT
145 PRINT
150 PRINT
GAME_START:
155 RANDOMIZE
160 LET N = INT(1945 * RND) + 1
175 PRINT
180 PRINT "ANY TIME YOU WANT ME TO RESHUFFLE THE CARDS SIMPLY"
185 PRINT "TYPE 777 WHEN I ASK FOR YOUR WAGER AND I'LL BE VERY"
190 PRINT "HAPPY TO OBLIGE. OK, HERE IS THE FIRST HAND."
200 LET X = INT(10 * RND(1))
210 DIM D(52), V(5), T(5), W(5), U(5)
238 DIM Q(52)
235 FOR A = 0 TO 3
240     FOR C = 1 TO 13
245         IF C > 10 THEN LET Q(A * 13 + C) = 10 ' Face cards
245         ELSE LET Q(A * 13 + C) = C
250     NEXT C
255 NEXT A
260 PRINT
265 FOR P = 1 TO 5
270     LET U(P) = 0
280 LET VCP = 0
285 LET TCP = T(P) - 10
290 NEXT P
295 LET V(3) = 1
300 PRINT
305 PRINT "PLACE YOUR WAGER (MAX $500)"
310 INPUT WAGER
315 IF WAGER > 500 THEN GOTO TOO_MUCH
320 IF WAGER < 1 THEN PRINT "MINIMUM BET IS $1.": GOTO 305
325 IF WAGER = 777 THEN GOTO RESHUFFLE
330 LET W(1) = WAGER
335 GOTO CONTINUE_GAME

TOO_MUCH:
    PRINT "THAT'S TOO MUCH - HOUSE LIMIT IS $500"
    GOTO 305

RESHUFFLE:
    ' Implement reshuffle logic here or call subroutine
    GOTO GAME_START

CONTINUE_GAME:
    PRINT "YOUR FIRST CARD IS "; INT(10 * RND(1))
    PRINT "YOUR SECOND CARD IS "; INT(10 * RND(1))
    PRINT "DEALER SHOWS "; INT(10 * RND(1))
    PRINT "YOUR MOVE: 0 - STAND, 1 - HIT, 2 - DOUBLE"
    INPUT MOVE
    IF MOVE = 0 THEN GOTO STAND
    IF MOVE = 1 THEN PRINT "YOU HIT.": GOTO HIT
    IF MOVE = 2 THEN PRINT "YOU DOUBLE.": LET W(1) = W(1) * 2: GOTO DOUBLE

HIT:
    PRINT "YOUR NEW CARD IS "; INT(10 * RND(1))
    PRINT "YOUR TOTAL IS "; V(1) + INT(10 * RND(1)) ' Update player value
    IF V(1) > 21 THEN PRINT "YOU BUST.": GOTO GAME_OVER
    GOTO CONTINUE_GAME

DOUBLE:
    ' Implement double logic
    GOTO GAME_OVER

STAND:
    PRINT "DEALER PLAYS."
    PRINT "DEALER'S TOTAL IS "; INT(17 + RND(1) * 5) ' Dealer logic
    IF V(1) > 21 THEN PRINT "YOU LOSE.": GOTO GAME_OVER
    IF V(1) = 21 THEN PRINT "BLACKJACK! YOU WIN.": GOTO GAME_OVER
    GOTO GAME_OVER

GAME_OVER:
    PRINT "GAME OVER. PLAY AGAIN? (Y/N)"
    INPUT A$
    IF UPPER$(A$) = "Y" THEN GOTO GAME_START
    END
340 IF WAGER = 777 THEN GOTO RESHUFFLE
345 GOTO CONTINUE_GAME
350 GOSUB 145
355 GOTO 315
360 PRINT "THAT'S TOO MUCH - HOUSE LIMIT IS $500"
365 GOTO 315
370 PRINT "I SHOW."
375 GOSUB 865
385 IF E(1) <> 0 THEN GOTO 395
390 LET V(1) = I ' Assumed variable assignment
395 LET V(S) = 0 ' Assumed variable assignment
400 GOSUB 865
405 LET M = X
410 LET P = 2
415 PRINT "FIRST CARD IS "; X
420 GOSUB 865
430 PRINT "NEXT CARD IS "; X
435 GOSUB 865
440 IF V(2) > 0 THEN GOTO 605
445 LET S = X
450 IF V(3) = 1 THEN GOTO 605
455 IF T(P) > 21 THEN GOTO 505
500 PRINT "BLACKJACK."
465 PRINT
470 PRINT "THAT'S GETTING RIDICULOUS!"
475 PRINT
480 PRINT "MY HOLE CARD IS " + STR$(X)
485 LET X = IM ' Unclear, assuming assignment
490 GOSUB 1035
495 LET I = 115 ' Assumed assignment
500 GOSUB 1335
505 IF V(4) > 10 THEN GOTO 570
510 PRINT "INSURANCE ANYONE? TYPE 1 OR 0. 1 MEANS YES"
515 INPUT I
520 PRINT
525 IF I = 1 THEN GOTO 570
530 IF I <> 21 THEN GOTO 550
535 LET W = W(P)
540 PRINT
545 PRINT "YOU WIN $" + STR$(W) + " ON YOUR INSURANCE BET"
550 GOTO 810
555 LET W = W(P) / 2
560 PRINT "YOU LOST $" + STR$(W) + " ON YOUR INSURANCE BET - YOU HAVE NO BLACKJACK"
565 IF T(CI) <> 21 THEN GOTO 805
570 PRINT "I HAVE BLACKJACK."
575 PRINT "MY HOLE CARD IS " + STR$(X)
580 GOTO 1300
585 PRINT "YOU HAVE BLACKJACK."
590 LET X = M
595 GOSUB 1035
600 GOTO 1300
605 IF T(P) > 21 THEN GOTO 650
610 IF E(P) > 0 THEN GOTO 640
615 PRINT "YOU TRUSTED,"
620 PRINT "YOUR TOTAL IS " + STR$(TCP)
625 LET C = T(P) - 5 * INT(T(P) / S)
630 GOTO 1175

635 LET E(P) = E(P) - I
640 LET T(P) = T(P) - 10
645 IF V(I) <> 2 THEN GOTO 620
650 LET V(3) = V(3) + 1
655 PRINT "QUIT"
660 INPUT V0
665 IF V(I) <> 3 THEN GOTO 830
670 IF V(2) <> 0 THEN GOTO 820
675 IF V(3) <= 1 THEN GOTO 820
680 IF Q(G) <> Q(S) THEN GOTO 700
685 PRINT "NOW IS THAT A PAIR"
690 GOTO 800
695 LET V(1,2) = 1
700 IF Q(G) = "I" THEN GOTO 115
705 LET V(1,2) = 2
710 PRINT "PLAY HAND ONE NOW"
715 PRINT "FIRST CARD IS "; X
720 GOSUB 710
725 GOTO 431
730 GOSUB 835
735 LET V(3) = 1
740 LET T(P) = C
745 RETURN
750 LET P = 2
755 PRINT "PLAY HAND TWO NOW"
760 PRINT "FIRST CARD IS "; X
765 GOSUB 750
770 IF Q(G) = 1 THEN GOTO 815
775 LET V(1) = 0
780 GOTO 430
785 PRINT "NO SPLITS NOW - TRY AGAIN"
790 GOTO 660
795 IF V(1) = 2 THEN GOTO 845
800 IF V(3) <> 2 THEN GOTO 850
805 PRINT "TOO LATE TO DOUBLE. CHALLENGE."
810 GOTO 660
815 LET W(P) = 2 + W(P)
820 IF V(1) > 0 THEN GOTO 430
825 GOTO 620
830 GOSUB 900
835 LET T(P) = T(P) + C
840 IF V(5) = 0 THEN GOTO 890
845 LET V(5) = 0
850 RETURN
855 GOSUB 1035
860 RETURN
865 IF R = 0 THEN GOTO 945
870 LET N = ABS(COS(N)) + W
880 FOR A = 1 TO N
890 LET X = INT(52.999999 * RND(V)) + 1
900 IF X = 0 THEN GOTO 915
910 IF D(X) <> 0 THEN GOTO 980
920 NEXT A
930 GOTO 865
870 IF R = 0 THEN GOTO 945
870 LET N = ABS(COS(N)) + W
875 FOR A = 1 TO N
880 LET X = INT(52.999999 * RND(V)) + 1
885 IF X = 0 THEN GOTO 915
890 NEXT A
895 IF D(X) <> 0 THEN GOTO 980
900 LET HOR = 1
905 IF R = 50 THEN GOTO 900
910 FOR A = 1 TO 52
915 IF D(A) <> K THEN GOTO 980
920 LET D(A) = 0
925 NEXT A
930 LET R = 0
935 PRINT "I RESHUFFLED"
940 GOTO 900
945 LET R = 0
950 LET D(X) = K
960 IF Q(X) = "I" THEN GOTO 1010
970 LET C = Q(X)
980 RETURN
950 LET R = 0
950 LET D(X) = K
955 IF Q(X) = "I" THEN GOTO 1010
960 LET C = Q(X)
965 RETURN
970 IF Q(X) > 10 THEN GOTO 1025
975 LET C = Q(X)
980 RETURN
985 LET C = Q(X)
990 RETURN
995 GOSUB 1050
1000 GOSUB 1120
1005 PRINT "HE TURN"
1010 IF Q(X) <> "I" THEN GOTO 1065
1015 PRINT "ACE " + I$
1020 RETURN
1025 IF Q(X) > 10 THEN GOTO 1080
1030 PRINT Q(X)
1035 RETURN
1040 RETURN
1050 LET CAU = ?
1055 RETURN
1060 GOSUB 1120
1065 PRINT "ACE " + I$
1070 GOTO 1080
1080 PRINT Q(X)
1085 RETURN
1090 IF Q(X) <> "I" THEN GOTO 1095
1095 PRINT "JACK"
1100 RETURN
1110 IF Q(X) <> 12 THEN GOTO 1115
1115 PRINT "QUEEN"
1120 RETURN
1125 PRINT "KING HE TURN"
1130 IF X > 39 THEN GOTO 1145
1135 IF X > 26 THEN GOTO 1150
1140 IF X > 13 THEN GOTO 1165
1145 PRINT "OF SPADES"
1150 RETURN
1155 PRINT "OF CLUBS"
1160 RETURN
1165 PRINT "OF HEARTS"
1170 RETURN
1175 PRINT "OF DIAMONDS"
1180 RETURN
1185 LET P = 2
1190 PRINT "MY HOLE CARD IS " + STR$(QO) + " " + STR$(IIA$)
1195 LET XOM = ?
1200 GOSUB 103B
1205 IF T(2) = 22 THEN GOTO 1210
1210 IF V(2) <> 0 THEN GOTO 1300
1215 IF T(3) > 21 THEN GOTO 1300
1220 LET P = 1
1225 IF T(I) = 11 THEN GOTO 1360
1230 IF T(O) > 11 THEN GOTO 1230 ' Possible typo, needs clarification
1235 IF E(I) > 0 THEN GOTO 1360
1240 IF T(I) > 21 THEN GOTO 1375
1245 LET T(P) = 2
1250 PRINT "YOUR TOTAL IS " + STR$(HI)
1300 PRINT "I BUSTED"
1305 GOTO 1235
1310 IF W < 0 THEN GOTO 1425
1315 IF W = 0 THEN GOTO 1435
1320 PRINT "YOU'RE AHEAD $" + STR$(W)
1325 RETURN
1330 PRINT USING "YOU'RE BEHIND $" + STR$(W)
1335 RETURN
1340 PRINT "YOU'RE EVEN"
1345 RETURN
1350 PRINT
1355 PRINT "I MUST HAVE DEALT WRONG"
1360 PRINT
1365 GOTO 1330
1370 PRINT
1375 PRINT "YOU LUCKED OUT AGAIN,"
1380 PRINT
1385 GOTO 1330
1390 PRINT
1395 PRINT "YOU MUST HAVE BEEN PEEKING,"
1400 PRINT
1405 GOTO 1330
1410 PRINT
1415 PRINT "I COULD LOSE MY JOB THIS WAY,"
1420 PRINT
1425 GOTO 1330
1430 PRINT
1435 PRINT "THE CARDS HAVE TURNED AGAINST ME"
1440 PRINT
1445 GOTO 1330
1450 PRINT
1455 PRINT "THE BOTTOM OF THE DECK STRIKES AGAIN:"
1460 PRINT
1465 GOTO 1330
1470 PRINT
1475 PRINT "A VICTORY FOR US GOOD GUYS,"
1480 PRINT
1485 GOTO 1330
1490 PRINT
1495 PRINT "YOU CAN'T BEAT SKILL,"
1500 PRINT
1505 GOTO 1330
1510 PRINT
1515 PRINT "YOU CAN'T WIN 'EM ALL,"
1520 PRINT
1525 GOTO 1330
1530 PRINT
1535 PRINT "BABY GETS A NEW PAIR OF SHOES"
1540 PRINT
1545 GOTO 1330
1550 FOR M9 = 1 TO 52
1555 LET D(M9) = 0
1560 NEXT M9
1570 LET R = 0
1580 PRINT
1585 PRINT "I RESHUFFLED"
1590 PRINT
1595 RETURN
1600 END