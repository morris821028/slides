#!/usr/bin/env python

import sys
import string
import random

def id_generator(size=6, chars=string.ascii_uppercase + string.digits):
    return ''.join(random.choice(chars) for _ in range(size))

def main(separator='\t', n=1000):
    # input comes from STDIN (standard input)
    for i in range(0, n, 1):
        print '%s%s%d' % (id_generator(4), separator, random.randint(0, 100))
 

if __name__ == "__main__":
    main()

