#!/usr/bin/env python

from itertools import groupby
from operator import itemgetter
import sys

def read_mapper_output(file, separator='\t'):
    for line in file:
        yield line.rstrip().split(separator, 1)

def main(separator='\t'):
    # input comes from STDIN (standard input)
    data = read_mapper_output(sys.stdin, separator=separator)
    for current_word, group in groupby(data, itemgetter(0)):
        try:
            ret = [0, 0]
            for current_word, value in group:
                t = map(int, value.split(separator, 2))
                ret[0] += t[0]
                ret[1] += t[1]
            print '%s%s%d%s%d' % (current_word, separator, ret[0], separator, ret[1])
        except ValueError:
            pass

if __name__ == "__main__":
    main()

