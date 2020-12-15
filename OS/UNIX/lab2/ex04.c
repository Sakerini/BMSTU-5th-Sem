/*
 * Написать программу, в которой предок и потомок обмениваются сообщением
 * через программный канал.
 */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

#define MSG_SIZE 19
void report_proc();

int main() {
    pid_t child1_pid = 0;
    int pipe_descr[2];
    if (pipe(pipe_descr) == -1) {
        puts("Can't pipe");
        exit(1);
    }

    if ((child1_pid = fork()) == -1) {
        puts("Can't create child 1");
        exit(1);
    } else if (child1_pid == 0) {
        // child 1
        printf(":::CHILD1::: pid %i group id %i parent id %i\n",
         getpid(), getpgrp(), getppid());

        close(pipe_descr[0]);
        char msg[] = "Hello from child 1";
        write(pipe_descr[1], msg, MSG_SIZE);
        exit(0);
    } else {
        int child2_pid = fork();
        if (child2_pid == -1) {
            puts("Can't create child 2");
            exit(1);
        } else if (child2_pid == 0) {
            // child 2
            printf(":::CHILD2::: pid %i group id %i parent id %i\n",
             getpid(), getpgrp(), getppid());

            close(pipe_descr[0]);
            char msg[] = "Hello from child 2";
            write(pipe_descr[1], msg, MSG_SIZE);
            exit(0);
        } else {
            // parent
            close(pipe_descr[1]);
            printf(":::PARENT:::");
            printf("pid %i group id %i parent id %i \n", \
                getpid(), getpgrp(), getppid());
            printf("child 1 id %i child 2 id %i\n",
                child1_pid, child2_pid);

            report_proc();
            report_proc();
            
            char buff[MSG_SIZE];
            
            while (read(pipe_descr[0], buff, MSG_SIZE) > 0) {
                printf("%s\n", buff);
            };
        }
    }

    return 0;
}

void report_proc() {
    int status = 0;
    pid_t wait_rc = wait(&status);

    if (WIFEXITED(status)) {
        printf(":::PARENT::: child %i finished with %i code.\n",
         wait_rc, WEXITSTATUS(status));
    } else if (WIFSIGNALED(status)) {
        printf(":::PARENT::: child %i finished from signal with %i code.\n",
         wait_rc, WTERMSIG(status));
    } else if (WIFSTOPPED(status)) {
        printf(":::PARENT::: child %i finished from signal with %i code.\n",
         wait_rc, WSTOPSIG(status));
    }
}