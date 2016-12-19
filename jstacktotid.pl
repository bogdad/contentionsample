#!/usr/bin/perl -w
# from http://stackoverflow.com/a/1199127
while (<>) {
    if (/nid=(0x[[:xdigit:]]+)/) {
        $lwp = hex($1);
        s/nid=/lwp=$lwp nid=/;
    }
    print;
}
