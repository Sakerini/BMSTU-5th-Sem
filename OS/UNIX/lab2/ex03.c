/*
 * Написать программу, в которой процесс-потомок вызывает системный вызов
 * exec(), а процесс-предок ждет завершения процесса-потомка.
 */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

void report_proc();

int main() {
    pid_t child1_pid;

    if ((child1_pid = fork()) == -1) {
        puts("Can't create child 1");
        exit(1);
    } else if (child1_pid == 0) {
        // child 1
        sleep(1);
        printf(":::CHILD1:::");
        prinf("pid %i group id %i parent id %i\n",
            getpid(), getpgrp(), getppid());
        if (execlp("/bin/ls", "ls", "-l", NULL) == -1) {
            puts(":::CHILD1::: Can not exec");
        }
    } else {
        int child2_pid = fork();
        if (child2_pid == -1) {
            puts("Can't create child 2");
            exit(1);
        } else if (child2_pid == 0) {
            // child 2
            printf(":::CHILD2:::");
            prinf("pid %i group id %i parent id %i\n",
                getpid(), getpgrp(), getppid());
            if (execlp("ps", "ps", "al", NULL) == -1) {
                puts(":::CHILD2::: Can not exec");
            }
        } else {
            // parent
            printf(":::PARENT:::");
            printf("pid %i group id %i parent id %i \n", \
                getpid(), getpgrp(), getppid());
            printf("child 1 id %i child 2 id %i\n",
                child1_pid, child2_pid);

            report_proc();
            report_proc();
        }
    }

    return 0;
}

void report_proc() {
    int status = 0;
    pid_t wait_rc = wait(&status);

    if (WIFEXITED(status)) {
        printf(":::PARENT::: child %i finished with %i code.\n", wait_rc, WEXITSTATUS(status));
    } else if (WIFSIGNALED(status)) {
        printf(":::PARENT::: child %i finished from signal with %i code.\n", wait_rc, WTERMSIG(status));
    } else if (WIFSTOPPED(status)) {
        printf(":::PARENT::: child %i finished from signal with %i code.\n", wait_rc, WSTOPSIG(status));
    }
}