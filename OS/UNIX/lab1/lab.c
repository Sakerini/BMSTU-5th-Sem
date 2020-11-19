#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main()
{
    int childpid;
    if ((childpid = fork()) == -1)
    {
        perror("Can’t fork.\n");
        return 1;
    }
    else if (childpid == 0)
    {
        // child code
        printf("CHILD PID %d  ", getpid());
        int n = 0;
        while (1) {
            printf("Child code running \n");
            n++;
        }
        return 0;
    }
    else
    {
        while (1)
            printf("%d  ", getpid());
            
        printf("PARENT PID %d \n", getpid());
        printf("Group %d\n", getpgrp());
        //parent code
        //while (1)
         //   printf("%d  ", getpid());
        printf("PARENT CODE RETURN \n");
        return 0;
    }
}