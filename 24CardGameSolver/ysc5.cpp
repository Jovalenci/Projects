#include<iostream>
#include<math.h>
const int n_op = 4; 
const int batas_atas = 13; 
using namespace std; 
int errorflag; 
long fact(long x){ //to make factorial function
	if(x==0){
		return 1; 
	} else if(x==1){
		return 1;
	}else{
		return x*fact(x-1);
	}
}
void tukar(int*array,int n1,int n2){ // to swap numbers
	int a = array[n1];
	array[n1] = array[n2]; 
	array[n2] = a; 
}
void permut(int*sumber,int*hasil,int size,long n){ // print all combinations
	for(int i=0;i<size;i++){
		hasil [i] = sumber [i];
	}
	for(int i=0;i<size-1;i++){
		tukar(hasil,i,n/fact(size-i-1)+i);
		n= n%fact(size-i-1);
	}
}
int isround(double x){
	if(floor(x)==x){
		return 1; 
	}else {
		return 0; 
	}
}
double hitung(double a,double b,int op){ // to define operators
	errorflag=0;
	if(op==1){
		return a+b; 
	} else if(op==2){
		return a-b;
	} else if(op==3){
		return a*b;
	} else if(op==4){
		return a/b; 
	} else if(op==5){
		if(a<10 && a>0 && b<10){
			return pow(a,b); 
		}else{
			errorflag = 1; 
			return -1; 
		}
		}else if(op==6){
			if(a>0 && a!=1 && b>0 ){//a = basis
			return log(b)/log(a);
			}else{
				errorflag = 1; 
				return -1; 
			}
		}else if(op==7){ //mod
		if((isround(a)==1 && isround(b) == 1) && b!=0){
			return (double)(((int)a) % ((int)b)); 
		}else{
			errorflag = 1;
			return -1; 
		}
	} 
}
double oper(int*angka,int*op,int*urutan,int n){ // define which numbers to solve first
	//op<angka
	double hasil;
	double*angka2;
	angka2 = new double[n];
	for(int i=0;i<n;i++){
		angka2[i]=angka[i]; 
	}
	for(int i=0;i<n-1;i++){
		int j = urutan[i]; 
		while(angka2[j]==0){
			j--; 
		}
		angka2[j] = hitung(angka2[j],angka2[urutan[i]+1],op[urutan[i]]);
		if(errorflag==1){
			return 0; 
		}
		angka2[urutan[i]+1]=0; 
	}
}
void tampil(int*angka,int*op,int*urutan,int n){ // to show the result
	for(int i=0;i<n-1;i++){
		cout<<angka[i];
		if(op[i]==1){
			cout<<'+';
		}else if(op[i]==2){
			cout<<'-';
		}else if(op[i]==3){
			cout<<'*';
		}else if(op[i]==4){
			cout<<'/';
		}else if(op[i]==5){
			cout<<'^';
		}else if(op[i]==6){
			cout<<"log"; 
		}else if(op[i]==7){
			cout<<"mod"; 
		}
	}
	cout<<angka[n-1]<<'='<<oper(angka,op,urutan,n);
	cout<<" Urutan:"; 
	for(int i=0;i<n-1;i++){
		cout<<urutan[i]<<" "; 
	}cout<<endl; 
}
long pangkat(long a,long b){ // to make exponentiation function
	long p = 1; 
	for(int i=0;i<b;i++){
		p = p*a;
	}return p; 
}
void permutop(int*hasil,int size,long n){ // to swap combinations of operators
	for(int i=0;i<size;i++){
		hasil [i] = n / pangkat(n_op,(size-i-1)) + 1; 
		n = n%pangkat(n_op,size-i-1); 
	}
}
int test(int*angka,int n,double target){ // try differennt operators with all combinations
	for(int i =0;i<fact(n);i++){
		int*angka2;
				angka2 = new int[n];
				permut(angka,angka2,n,i);
		for(int i2 =0;i2<pangkat(n_op,n-1);i2++){
				int*op;
				op = new int[n-1];
				permutop(op,n-1,i2); 
			for(int i3 =0;i3<fact(n-1);i3++){
				int*urut; 
				urut = new int[n-1]; 
				for(int i4=0;i4<n-1;i4++){
					urut[i4]=i4; 
				}
				int*urut2;
				urut2 = new int[n-1];
				permut(urut,urut2,n-1,i3);
				double hasil = oper(angka2,op,urut2,n);
				if(hasil==target){
					tampil(angka2,op,urut2,n);
					return 1; 
				}
			}
		}
	}return 0; 
}
int main(){ // print all combinations depends on input target
	int target,ns=0,ng=0; 
	cin>>target; 
	int A[4];
	for(int i=1;i<=batas_atas;i++){
		for(int i2=i;i2<=batas_atas;i2++){
			for(int i3=i2;i3<=batas_atas;i3++){
				for(int i4=i3;i4<=batas_atas;i4++){
					cout<<i<<" "<<i2<<" "<<i3<<" "<<i4<<endl;
					A[0]=i; A[1]=i2; A[2]=i3; A[3]=i4;
					if(test(A,4,target)==1){
						ns++; 
					}else{
						ng++; 
					}
				}
			}
		}	
	} cout<<"Berhasil : "<<ns<<endl;
	cout<<"Gagal : "<<ng<<endl;  	
}

