/*
 * Написать программу, запускающую новый процесс системным вызовом fork().
 * В предке вывести собственный идентификатор ( функция getpid()), идентификатор
 * группы ( функция getpgrp()) и идентификатор потомка. В процессе-потомке
 * вывести собственный идентификатор, идентификатор предка ( функция getppid()) и
 * идентификатор группы. Убедиться, что при завершении процесса-предка потомок,
 * который продолжает выполняться, получает идентификатор предка (PPID), равный
 * 1 или идентификатор процесса-посредника.
 */
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>

int main() {
    pid_t child1_pid, child2_pid;

    if ((child1_pid = fork()) == -1) {
        puts("Can't create new proc");
        exit(1);
    } else if (child1_pid == 0) {
        sleep(1);
        printf("CHILD1\n");
        printf("pid %i group id %i parent id %i\n",
        getpid(), getpgrp(), getppid());
        exit(0);
    } 

    if ((child2_pid = fork()) == -1) {
        puts("Can't create new proc");
        exit(1);
    } else if (child2_pid == 0) {
        sleep(1);
        printf("CHILD2\n");
        printf("pid %i group id %i parent id %i\n",
        getpid(), getpgrp(), getppid());
        exit(0);
    }

    //sleep(1);
    printf("PARENT\n");
    printf("pid %i group id %i\n", \
            getpid(), getpgrp());
    printf("child 1 id %i child 2 id %i\n",
    child1_pid, child2_pid);

    return 0;
}